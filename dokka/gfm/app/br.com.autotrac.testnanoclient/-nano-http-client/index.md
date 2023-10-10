//[app](../../../index.md)/[br.com.autotrac.testnanoclient](../index.md)/[NanoHttpClient](index.md)

# NanoHttpClient

[androidJvm]\
object [NanoHttpClient](index.md)

Cliente de integração para o servidor NanoHTTPD. Utiliza OkHttpClient e Retrofit.

#### Author

Melina Minaya

## Properties

| Name | Summary |
|---|---|
| [client](client.md) | [androidJvm]<br>val [client](client.md): OkHttpClient.Builder |
| [maxChunkSize](max-chunk-size.md) | [androidJvm]<br>val [maxChunkSize](max-chunk-size.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 8192 |
| [TAG](-t-a-g.md) | [androidJvm]<br>const val [TAG](-t-a-g.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [webSocket](web-socket.md) | [androidJvm]<br>var [webSocket](web-socket.md): WebSocket? |

## Functions

| Name | Summary |
|---|---|
| [sendFileChunksHttp](send-file-chunks-http.md) | [androidJvm]<br>suspend fun [sendFileChunksHttp](send-file-chunks-http.md)(endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md), maxChunkSize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), fileUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Quando enviar mensagens longas será necessário utilizar um método com inicialização de um websocket somente para enviar os dados. Após recebimento das mensagens, o websocket poderá ser fechado. |
| [sendGetRequestHttp](send-get-request-http.md) | [androidJvm]<br>suspend fun [sendGetRequestHttp](send-get-request-http.md)(endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), objectGet: [RequestObject](../../br.com.autotrac.testnanoclient.requestObjects/-request-object/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Método para envio de todas as requisições, menos a de mensagens longas [ApiEndpoints.SEND_FILE_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-companion/-s-e-n-d_-f-i-l-e_-m-e-s-s-a-g-e.md). |
