package jvmdap.server.debuggees

import com.sun.jdi.Bootstrap
import com.sun.jdi.VirtualMachine
import jvmdap.server.DAPException
import java.io.InputStream

const val DEFAULT_TIMEOUT_MS = 5000

open class ProcessLauncher(private val builder: ProcessBuilder): VmCreator {
    lateinit var process: Process

    override fun createJvm(): VirtualMachine {
        val vmm = Bootstrap.virtualMachineManager()
        val connector = vmm.listeningConnectors().find { it.name() == "com.sun.jdi.SocketListen" }
            ?: throw DAPException("Couldn't find SocketListen JDI connector")

        val args = connector.defaultArguments().also {
            it["localAddress"]?.setValue("127.0.0.1")
            it["timeout"]?.setValue("$DEFAULT_TIMEOUT_MS")
        }
        builder.environment().also {
            it["JAVA_TOOL_OPTIONS"] = appendDebugToToolOptions(it["JAVA_TOOL_OPTIONS"], connector.startListening(args))
        }
        process = builder.start()
        return connector.accept(args).also {
            connector.stopListening(args)
        }
    }

    override fun getStderrStream(): InputStream? {
        return process.errorStream
    }

    override fun getStdoutStream(): InputStream? {
        return process.inputStream
    }
}
//
fun appendDebugToToolOptions(toolOptions: String?, address: String): String {
    val options = toolOptions ?: ""
    if ("-agentlib:jdwp" in options) {
        throw DAPException("Error: JAVA_TOOL_OPTIONS is already configured with debugging!")
    }
    return "-agentlib:jdwp=transport=dt_socket,server=n,suspend=y,address=${address} " + options
}