//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[MessageViewModel](index.md)

# MessageViewModel

[androidJvm]\
open class [MessageViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [MessageViewModel](-message-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [contentResolverLiveData](content-resolver-live-data.md) | [androidJvm]<br>val [contentResolverLiveData](content-resolver-live-data.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html)&gt; |
| [messages](messages.md) | [androidJvm]<br>val [messages](messages.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)&gt;&gt; |
| [outboxMessages](outbox-messages.md) | [androidJvm]<br>val [outboxMessages](outbox-messages.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [deleteMessage](delete-message.md) | [androidJvm]<br>fun [deleteMessage](delete-message.md)(message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)) |
| [deleteMessageOutbox](delete-message-outbox.md) | [androidJvm]<br>fun [deleteMessageOutbox](delete-message-outbox.md)(message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)) |
| [fetchDataFromDataSource](fetch-data-from-data-source.md) | [androidJvm]<br>suspend fun [fetchDataFromDataSource](fetch-data-from-data-source.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)&gt;? |
| [fetchMessages](fetch-messages.md) | [androidJvm]<br>suspend fun [fetchMessages](fetch-messages.md)() |
| [fetchOutboxMessages](fetch-outbox-messages.md) | [androidJvm]<br>suspend fun [fetchOutboxMessages](fetch-outbox-messages.md)() |
| [markMessageAsRead](mark-message-as-read.md) | [androidJvm]<br>fun [markMessageAsRead](mark-message-as-read.md)(message: [IntegrationMessage](../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/index.md)) |
| [sendMessageAndWait](send-message-and-wait.md) | [androidJvm]<br>suspend fun [sendMessageAndWait](send-message-and-wait.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setContentResolver](set-content-resolver.md) | [androidJvm]<br>fun [setContentResolver](set-content-resolver.md)(contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html)) |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
