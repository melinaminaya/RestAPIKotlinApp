//[app](../../../index.md)/[br.com.autotrac.testnanoclient.requestObjects](../index.md)/[SendObject](index.md)

# SendObject

data class [SendObject](index.md)(val param1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?)

O SendObject é o objeto final a ser enviado ao servidor.

Exemplo de requisição de Notificação: {&quot;param1&quot;:&quot;NOTIFICATION&quot;,&quot;param2&quot;:22,7,10,18,21,24,15,9,8,11,5,23,4,3}

Exemplo de requisição REQUEST sem parâmetros: {&quot;param1&quot;:&quot;REQ_GET_CHECKLIST&quot;,&quot;param2&quot;:{}}

Exemplo de requisição PARAMETER com parâmetros: {&quot;param1&quot;:&quot;SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S&quot;,&quot;param2&quot;:{&quot;param1&quot;:&quot;10&quot;}}

#### Parameters

androidJvm

| | |
|---|---|
| param1 | refere-se ao endpoint. |
| param2 | refere-se ao objeto a ser enviado no formato de [RequestObject](../-request-object/index.md), caso seja uma REQUEST ou PARAMETER. |

## Constructors

| | |
|---|---|
| [SendObject](-send-object.md) | [androidJvm]<br>constructor(param1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [param1](param1.md) | [androidJvm]<br>val [param1](param1.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [param2](param2.md) | [androidJvm]<br>val [param2](param2.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
