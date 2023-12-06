//[app](../../index.md)/[br.com.autotrac.testnanoclient.screens](index.md)/[messageOnPattern](message-on-pattern.md)

# messageOnPattern

[androidJvm]\
suspend fun [messageOnPattern](message-on-pattern.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), selectedFileString: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)?, selectedFileName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [IntegrationMessage](../br.com.autotrac.testnanoclient.models/-integration-message/index.md)

Transforma a messagem para o DataClasse de Transmissão [IntegrationMessage](../br.com.autotrac.testnanoclient.models/-integration-message/index.md). Caso de exemplo a mensagem é enviada como texto, portanto seta o [IntegrationMessage.msgSubtype](../br.com.autotrac.testnanoclient.models/-integration-message/msg-subtype.md) == 0. Caso a mensagem seja enviada como byteArray (Binária), seta o [IntegrationMessage.msgSubtype](../br.com.autotrac.testnanoclient.models/-integration-message/msg-subtype.md) == 1.
