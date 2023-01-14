package jvmdap.server.util

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier

open class AsyncComputer {
    private val computer = Executors.newSingleThreadExecutor()

    fun <T> supplyAsync(t: Supplier<T>): CompletableFuture<T> {
        return CompletableFuture.supplyAsync(t, computer)
    }

    fun submit(t: Runnable) {
        computer.submit(t)
    }
}