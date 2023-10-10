//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[ParametersViewModel](index.md)

# ParametersViewModel

[androidJvm]\
class [ParametersViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html), [ParseOnMessage.NotificationListener](../../br.com.autotrac.testnanoclient.handlers/-parse-on-message/-notification-listener/index.md)

## Constructors

| | |
|---|---|
| [ParametersViewModel](-parameters-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [isBaptizedValue](is-baptized-value.md) | [androidJvm]<br>val [isBaptizedValue](is-baptized-value.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [fetchParameters](fetch-parameters.md) | [androidJvm]<br>suspend fun [fetchParameters](fetch-parameters.md)() |
| [getParameterLiveData](get-parameter-live-data.md) | [androidJvm]<br>fun [getParameterLiveData](get-parameter-live-data.md)(parameter: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
| [onNotificationReceived](on-notification-received.md) | [androidJvm]<br>open override fun [onNotificationReceived](on-notification-received.md)(notification: [ReceivedRequestResponse](../../br.com.autotrac.testnanoclient.requestObjects/-received-request-response/index.md)) |
| [setParam](set-param.md) | [androidJvm]<br>fun [setParam](set-param.md)(param: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
