//[app](../../../index.md)/[br.com.autotrac.testnanoclient.dataRemote](../index.md)/[ChunkObject](index.md)

# ChunkObject

[androidJvm]\
data class [ChunkObject](index.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), totalChunks: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), chunkNumber: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), data: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Objeto a ser enviado quando envia-se um arquivo por meio de [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_FILE_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-const-endpoints/-companion/-s-e-n-d_-f-i-l-e_-m-e-s-s-a-g-e.md). Também utilizado no envio de mensagens por meio de [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-const-endpoints/-companion/-s-e-n-d_-m-e-s-s-a-g-e.md) via WebSocket. Via HTTP o [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_MESSAGE](../../br.com.autotrac.testnanoclient.consts/-api-const-endpoints/-companion/-s-e-n-d_-m-e-s-s-a-g-e.md) é enviado via requisição normal, porém mensagens longas e/ou com arquivos serão enviadas por meio de WebSocket e portanto o objeto [ChunkObject](index.md) também será utilizado. Por fim este objeto será encapsulado como param2 no [SendObject](../-send-object/index.md).

## Constructors

| | |
|---|---|
| [ChunkObject](-chunk-object.md) | [androidJvm]<br>fun [ChunkObject](-chunk-object.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), totalChunks: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), chunkNumber: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), data: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [chunkNumber](chunk-number.md) | [androidJvm]<br>val [chunkNumber](chunk-number.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [data](data.md) | [androidJvm]<br>val [data](data.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [message](message.md) | [androidJvm]<br>val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [totalChunks](total-chunks.md) | [androidJvm]<br>val [totalChunks](total-chunks.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
