//[app](../../../index.md)/[br.com.autotrac.testnanoclient.di](../index.md)/[ApiService](index.md)

# ApiService

[androidJvm]\
interface [ApiService](index.md)

## Functions

| Name | Summary |
|---|---|
| [callRequest](call-request.md) | [androidJvm]<br>@GET(value = "/{endpoint}")<br>abstract fun [callRequest](call-request.md)(@Header(value = "authorization")authorization: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Path(value = "endpoint")endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = "param1")param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = "param2")param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = "param3")param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = "param4")param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): Call&lt;ResponseBody&gt;<br>Requisição Genérica de endpoints |
