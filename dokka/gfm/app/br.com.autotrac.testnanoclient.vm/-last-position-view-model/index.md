//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[LastPositionViewModel](index.md)

# LastPositionViewModel

[androidJvm]\
open class [LastPositionViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [LastPositionViewModel](-last-position-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [lastPosition](last-position.md) | [androidJvm]<br>val [lastPosition](last-position.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[LastPosition](../../br.com.autotrac.testnanoclient.dataRemote/-last-position/index.md)&gt; |
| [positionHistoryCount](position-history-count.md) | [androidJvm]<br>val [positionHistoryCount](position-history-count.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [positionHistoryList](position-history-list.md) | [androidJvm]<br>val [positionHistoryList](position-history-list.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PositionHistory](../../br.com.autotrac.testnanoclient.dataRemote/-position-history/index.md)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [fetchPositionHistoryCount](fetch-position-history-count.md) | [androidJvm]<br>suspend fun [fetchPositionHistoryCount](fetch-position-history-count.md)()<br>Somente para o PrimeMobile: MESSAGE_DUPLICATE	-5514 MESSAGE_TOO_BIG	-5531 NONE	0 NOT_PROCESSED	5 NOT_READ	3 NOT_READ_OUT_OF_BAND	7 OUT_OF_BAND_FILE_NOT_FOUND	-5561 READ	4 SENT	2 SERIAL_MESSAGE_CELL_NET_UNAVAILABLE	1001 SERIAL_MESSAGE_TOO_BIG_WAITING	1000 TO_SEND	1 TRANSMITTED	6 TRANSMITTING	8 UNKNOWN_ERROR	-5599 |
| [fetchPositionHistoryList](fetch-position-history-list.md) | [androidJvm]<br>suspend fun [fetchPositionHistoryList](fetch-position-history-list.md)() |
| [fetchPositionLast](fetch-position-last.md) | [androidJvm]<br>suspend fun [fetchPositionLast](fetch-position-last.md)() |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
