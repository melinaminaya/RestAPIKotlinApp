//[app](../../../index.md)/[br.com.autotrac.testnanoclient](../index.md)/[NanoWebsocketClient](index.md)

# NanoWebsocketClient

[androidJvm]\
object [NanoWebsocketClient](index.md)

Cliente WebSocket baseado em OKHttpClient e Retrofit.

#### Author

Melina Minaya.

## Properties

| Name | Summary |
|---|---|
| [connectionDisposable](connection-disposable.md) | [androidJvm]<br>var [connectionDisposable](connection-disposable.md): Disposable? = null |
| [currentMsgCode](current-msg-code.md) | [androidJvm]<br>var [currentMsgCode](current-msg-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [gson](gson.md) | [androidJvm]<br>val [gson](gson.md): Gson |
| [packageName](package-name.md) | [androidJvm]<br>const val [packageName](package-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [responseListener](response-listener.md) | [androidJvm]<br>var [responseListener](response-listener.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [TAG](-t-a-g.md) | [androidJvm]<br>const val [TAG](-t-a-g.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [connect](connect.md) | [androidJvm]<br>fun [connect](connect.md)()<br>Método para conexão do WebSocket Cliente ao Servidor. |
| [disconnect](disconnect.md) | [androidJvm]<br>fun [disconnect](disconnect.md)() |
| [getInstance](get-instance.md) | [androidJvm]<br>fun [getInstance](get-instance.md)(): [NanoWebsocketClient](index.md) |
| [isWebSocketConnected](is-web-socket-connected.md) | [androidJvm]<br>fun [isWebSocketConnected](is-web-socket-connected.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [requestAuthorizationToken](request-authorization-token.md) | [androidJvm]<br>fun [requestAuthorizationToken](request-authorization-token.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Requisição de autenticação do WebSocket e reusado no HTTP. Insere package name da sua aplicação para ser reconhecido pelo Servidor. Insere linguagem de prefência para respostas do Servidor. |
| [retryConnection](retry-connection.md) | [androidJvm]<br>fun [retryConnection](retry-connection.md)()<br>Método para tentativa de reconexão ao Servidor. |
| [sendDbMessage](send-db-message.md) | [androidJvm]<br>suspend fun [sendDbMessage](send-db-message.md)(message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md), fileUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)?, context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |
| [sendMessage](send-message.md) | [androidJvm]<br>fun [sendMessage](send-message.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendMessageFromClient](send-message-from-client.md) | [androidJvm]<br>fun [sendMessageFromClient](send-message-from-client.md)()<br>Envia a lista de endpoint de notificações que desejam ser escutadas. |
| [sendMessageRequest](send-message-request.md) | [androidJvm]<br>fun [sendMessageRequest](send-message-request.md)(param: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [startSendingRequests](start-sending-requests.md) | [androidJvm]<br>fun [startSendingRequests](start-sending-requests.md)()<br>Método fixo de requesição de mensagens a cada período de tempo. |
