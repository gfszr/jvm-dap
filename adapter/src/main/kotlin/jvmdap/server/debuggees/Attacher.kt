package jvmdap.server.debuggees

import com.sun.jdi.Bootstrap
import com.sun.jdi.VirtualMachine
import com.sun.jdi.connect.Connector
import jvmdap.server.DAPException
import java.io.InputStream

class Attacher(private val host: String, private val targetPort: Int): VmCreator {
    override fun createJvm(): VirtualMachine {
        val vmm = Bootstrap.virtualMachineManager()
        val connector = vmm.attachingConnectors().find {
            it.name() == "com.sun.jdi.SocketAttach"
        } ?: throw DAPException("SocketAttach not found")

        return connector.attach(connector.defaultArguments().also {
            it["hostname"]?.setValue(host)
            it["port"]?.setValue(targetPort.toString())
        })
    }
}