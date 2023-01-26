package jvmdap.server.debuggees

import com.sun.jdi.Bootstrap
import com.sun.jdi.VirtualMachine
import jvmdap.server.DAPException
import jvmdap.server.configurations.AttachConfiguration

class PortAttacher(private val config: AttachConfiguration): VmCreator {
    override fun createJvm(): VirtualMachine {
        val vmm = Bootstrap.virtualMachineManager()
        val connector = vmm.attachingConnectors().find {
            it.name() == "com.sun.jdi.SocketAttach"
        } ?: throw DAPException("SocketAttach not found")

        return connector.attach(connector.defaultArguments().also {
            it["hostname"]?.setValue(config.host)
            it["port"]?.setValue("${config.port}")
        })
    }
}