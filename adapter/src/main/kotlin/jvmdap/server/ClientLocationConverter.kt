package jvmdap.server

class ClientLocationConverter(clientLineStartsAt1: Boolean, clientColumnStartsAt1: Boolean) {
    private val lineDelta = if (clientLineStartsAt1) -1 else 0
    private val columnDelta = if (clientColumnStartsAt1) -1 else 0
    fun lineFromClient(line: Int) = line + lineDelta
    fun lineToClient(line: Int) = line - lineDelta
    fun columnFromClient(column: Int) = column + columnDelta
    fun columnToClient(column: Int) = column - columnDelta
}