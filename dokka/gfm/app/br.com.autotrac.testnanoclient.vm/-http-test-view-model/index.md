//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[HttpTestViewModel](index.md)

# HttpTestViewModel

[androidJvm]\
open class [HttpTestViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Properties

| Name | Summary |
|---|---|
| [reqMessageCount](req-message-count.md) | [androidJvm]<br>val [reqMessageCount](req-message-count.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [reqMessageList](req-message-list.md) | [androidJvm]<br>val [reqMessageList](req-message-list.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)&gt;&gt; |
| [responseRequest](response-request.md) | [androidJvm]<br>val [responseRequest](response-request.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [fetchRequest](fetch-request.md) | [androidJvm]<br>suspend fun [fetchRequest](fetch-request.md)(url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), requestObject: [RequestObject](../../br.com.autotrac.testnanoclient.dataRemote/-request-object/index.md), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))<br>Método que requisita de acordo com o endpoint e filtro específico. Retorna a resposta da requisição Http. |
| [messageCountHttp](message-count-http.md) | [androidJvm]<br>suspend fun [messageCountHttp](message-count-http.md)()<br>Método para retornar contagem de mensagens de acordo com o filtro especificado. No caso abaixo, a requisição é feita para contagem de mensagens não lidas. |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [clear](../-reset-database-view-model/index.md#-1936886459%2FFunctions%2F-912451524) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>fun [clear](../-reset-database-view-model/index.md#-1936886459%2FFunctions%2F-912451524)() |
| [getTag](../-reset-database-view-model/index.md#-215894976%2FFunctions%2F-912451524) | [androidJvm]<br>open fun &lt;[T](../-reset-database-view-model/index.md#-215894976%2FFunctions%2F-912451524) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [getTag](../-reset-database-view-model/index.md#-215894976%2FFunctions%2F-912451524)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../-reset-database-view-model/index.md#-215894976%2FFunctions%2F-912451524) |
| [onCleared](../-reset-database-view-model/index.md#-1930136507%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onCleared](../-reset-database-view-model/index.md#-1930136507%2FFunctions%2F-912451524)() |
| [setTagIfAbsent](../-reset-database-view-model/index.md#-1567230750%2FFunctions%2F-912451524) | [androidJvm]<br>open fun &lt;[T](../-reset-database-view-model/index.md#-1567230750%2FFunctions%2F-912451524) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [setTagIfAbsent](../-reset-database-view-model/index.md#-1567230750%2FFunctions%2F-912451524)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), p1: [T](../-reset-database-view-model/index.md#-1567230750%2FFunctions%2F-912451524)): [T](../-reset-database-view-model/index.md#-1567230750%2FFunctions%2F-912451524) |
