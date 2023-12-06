//[app](../../../index.md)/[br.com.autotrac.testnanoclient.retrofit](../index.md)/[ApiService](index.md)

# ApiService

[androidJvm]\
interface [ApiService](index.md)

## Functions

| Name | Summary |
|---|---|
| [callRequest](call-request.md) | [androidJvm]<br>@GET(value = &quot;/{endpoint}&quot;)<br>abstract fun [callRequest](call-request.md)(@Header(value = &quot;authorization&quot;)authorization: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Path(value = &quot;endpoint&quot;)endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = &quot;param1&quot;)param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param2&quot;)param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param3&quot;)param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param4&quot;)param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): Call&lt;ResponseBody&gt;<br>Requisição Genérica de endpoints por meio de chamadas HTTP. |
