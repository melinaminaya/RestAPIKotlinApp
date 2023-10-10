//[app](../../../index.md)/[br.com.autotrac.testnanoclient.requestObjects](../index.md)/[ReceivedRequestResponse](index.md)

# ReceivedRequestResponse

data class [ReceivedRequestResponse](index.md)(val param1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val param2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?)

O ReceivedRequestResponse refere-se ao objeto que é recebido pelo WebSocket ou requisições HTTP.

Exemplo de ReceivedRequestResponse(param1=REQUEST, param2=REQ_MESSAGE_COUNT, param3={param1=false, param2=3}, param4=0.0)

#### Parameters

androidJvm

| | |
|---|---|
| param1 | Referencia aos Endpoints Gerais de [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQUEST](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-companion/-r-e-q-u-e-s-t.md), [br.com.autotrac.testnanoclient.consts.ApiEndpoints.NOTIFICATION](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-companion/-n-o-t-i-f-i-c-a-t-i-o-n.md) e [br.com.autotrac.testnanoclient.consts.ApiEndpoints.PARAMETER](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-companion/-p-a-r-a-m-e-t-e-r.md). |
| param2 | Referencia aos Endpoints Específicos |
| param3 | Referencia o filtro da mensagem ou requisição solicitada |
| param4 | Valor, resposta do servidor. |

## Constructors

| | |
|---|---|
| [ReceivedRequestResponse](-received-request-response.md) | [androidJvm]<br>constructor(param1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, param2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [param1](param1.md) | [androidJvm]<br>val [param1](param1.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [param2](param2.md) | [androidJvm]<br>val [param2](param2.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [param3](param3.md) | [androidJvm]<br>val [param3](param3.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [param4](param4.md) | [androidJvm]<br>val [param4](param4.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
