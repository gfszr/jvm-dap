package jvmdap.server.debuggees

import com.sun.jdi.VirtualMachine
import java.io.InputStream

interface VmCreator {
    fun createJvm(): VirtualMachine
    fun getStdoutStream(): InputStream? {
       return null
    }
    fun getStderrStream(): InputStream? {
        return null
    }
}