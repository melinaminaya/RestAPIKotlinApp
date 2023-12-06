//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[AppViewModel](index.md)

# AppViewModel

[androidJvm]\
open class [AppViewModel](index.md)(state: [SavedStateHandle](https://developer.android.com/reference/kotlin/androidx/lifecycle/SavedStateHandle.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [AppViewModel](-app-view-model.md) | [androidJvm]<br>constructor(state: [SavedStateHandle](https://developer.android.com/reference/kotlin/androidx/lifecycle/SavedStateHandle.html)) |

## Properties

| Name | Summary |
|---|---|
| [isApiOn](is-api-on.md) | [androidJvm]<br>val [isApiOn](is-api-on.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [isMobileCommunicatorOn](is-mobile-communicator-on.md) | [androidJvm]<br>val [isMobileCommunicatorOn](is-mobile-communicator-on.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [logs](logs.md) | [androidJvm]<br>val [logs](logs.md): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [checkMobileCommunicator](check-mobile-communicator.md) | [androidJvm]<br>fun [checkMobileCommunicator](check-mobile-communicator.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [connectCommunicator](connect-communicator.md) | [androidJvm]<br>fun [connectCommunicator](connect-communicator.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), coroutineScope: CoroutineScope, snackbarHostState: [SnackbarHostState](https://developer.android.com/reference/kotlin/androidx/compose/material3/SnackbarHostState.html)) |
| [connectToWebSocket](connect-to-web-socket.md) | [androidJvm]<br>fun [connectToWebSocket](connect-to-web-socket.md)(snackbarHostState: [SnackbarHostState](https://developer.android.com/reference/kotlin/androidx/compose/material3/SnackbarHostState.html), coroutineScope: CoroutineScope, context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |
| [disconnectCommunicator](disconnect-communicator.md) | [androidJvm]<br>fun [disconnectCommunicator](disconnect-communicator.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), coroutineScope: CoroutineScope, snackbarHostState: [SnackbarHostState](https://developer.android.com/reference/kotlin/androidx/compose/material3/SnackbarHostState.html)) |
| [disconnectWebsocket](disconnect-websocket.md) | [androidJvm]<br>fun [disconnectWebsocket](disconnect-websocket.md)() |
| [registerLogs](register-logs.md) | [androidJvm]<br>fun [registerLogs](register-logs.md)() |
| [setIsMobileCommunicatorOn](set-is-mobile-communicator-on.md) | [androidJvm]<br>fun [setIsMobileCommunicatorOn](set-is-mobile-communicator-on.md)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [startCheckingApiStatus](start-checking-api-status.md) | [androidJvm]<br>fun [startCheckingApiStatus](start-checking-api-status.md)() |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
