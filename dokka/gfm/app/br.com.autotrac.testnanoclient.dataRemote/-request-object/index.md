//[app](../../../index.md)/[br.com.autotrac.testnanoclient.dataRemote](../index.md)/[RequestObject](index.md)

# RequestObject

[androidJvm]\
data class [RequestObject](index.md)(param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?)

Objeto a ser enviado em [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.REQUEST](../../br.com.autotrac.testnanoclient.consts/-api-const-endpoints/-companion/-r-e-q-u-e-s-t.md) e [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.PARAMETER](../../br.com.autotrac.testnanoclient.consts/-api-const-endpoints/-companion/-p-a-r-a-m-e-t-e-r.md) contendo os parâmetros necessários para cada requisição ao servidor. Ao enviar via WebSocket, o objeto somente é convertido para gson e encapsulado dentro do [SendObject](../-send-object/index.md). Por meio de requisição HTTP, o objeto é distribuído nas 4 queries enviadas ao servidor.

## Constructors

| | |
|---|---|
| [RequestObject](-request-object.md) | [androidJvm]<br>fun [RequestObject](-request-object.md)(param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [param1](param1.md) | [androidJvm]<br>val [param1](param1.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [param2](param2.md) | [androidJvm]<br>val [param2](param2.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [param3](param3.md) | [androidJvm]<br>val [param3](param3.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [param4](param4.md) | [androidJvm]<br>val [param4](param4.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
