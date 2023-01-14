package jvmdap.server

import jvmdap.server.util.AsyncComputer
import org.eclipse.lsp4j.debug.Capabilities
import org.eclipse.lsp4j.debug.DisconnectArguments
import org.eclipse.lsp4j.debug.InitializeRequestArguments
import org.eclipse.lsp4j.debug.launch.DSPLauncher
import java.io.InputStream
import java.io.OutputStream
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer
import org.eclipse.lsp4j.jsonrpc.CompletableFutures
import java.util.concurrent.CompletableFuture


class DapServer(private val input: InputStream, private val output: OutputStream): IDebugProtocolServer, AsyncComputer() {
    private val launcher = DSPLauncher.createServerLauncher(this, input, output)
    private val client = launcher.remoteProxy
    private val serverDone = launcher.startListening()
    private lateinit var lineConverter: ClientLocationConverter

    fun waitForTermination() {
        try {
            serverDone.get()
        } catch (t: Throwable) {
        }
    }
    override fun initialize(args: InitializeRequestArguments) = supplyAsync {
        lineConverter = ClientLocationConverter(args.linesStartAt1, args.columnsStartAt1)
        Capabilities().also {
            it.supportsConfigurationDoneRequest = true
        }
    }

    override fun disconnect(args: DisconnectArguments): CompletableFuture<Void> {
        return CompletableFuture.completedFuture(null)
    }
}