//[app](../../../index.md)/[br.com.autotrac.testnanoclient](../index.md)/[NanoHttpClient](index.md)/[sendFileChunksHttp](send-file-chunks-http.md)

# sendFileChunksHttp

[androidJvm]\
suspend fun [sendFileChunksHttp](send-file-chunks-http.md)(endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md), maxChunkSize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), fileUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Quando enviar mensagens longas será necessário utilizar um método com inicialização de um websocket somente para enviar os dados. Após recebimento das mensagens, o websocket poderá ser fechado.
