package br.com.autotrac.testnanoclient.data

interface WebSocketConnectionCallback {
    fun onConnectionSuccess()
    fun onConnectionFailure()
}
interface WebSocketDisconnectionCallback {
    fun onDisconnectionSuccess()
    fun onDisconnectionFailure()
}