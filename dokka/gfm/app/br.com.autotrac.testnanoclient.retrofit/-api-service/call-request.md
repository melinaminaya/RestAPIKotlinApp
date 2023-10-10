//[app](../../../index.md)/[br.com.autotrac.testnanoclient.retrofit](../index.md)/[ApiService](index.md)/[callRequest](call-request.md)

# callRequest

[androidJvm]\

@GET(value = &quot;/{endpoint}&quot;)

abstract fun [callRequest](call-request.md)(@Header(value = &quot;authorization&quot;)authorization: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Path(value = &quot;endpoint&quot;)endpoint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = &quot;param1&quot;)param1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param2&quot;)param2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param3&quot;)param3: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, @Query(value = &quot;param4&quot;)param4: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): Call&lt;ResponseBody&gt;

Requisição Genérica de endpoints
