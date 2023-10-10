//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[CheckListViewModel](index.md)

# CheckListViewModel

[androidJvm]\
open class [CheckListViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [CheckListViewModel](-check-list-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [cellSignal](cell-signal.md) | [androidJvm]<br>val [cellSignal](cell-signal.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [checkList](check-list.md) | [androidJvm]<br>val [checkList](check-list.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[CheckList](../../br.com.autotrac.testnanoclient.dataRemote/-check-list/index.md)&gt;&gt; |
| [wifiSignal](wifi-signal.md) | [androidJvm]<br>val [wifiSignal](wifi-signal.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [fetchCellSignal](fetch-cell-signal.md) | [androidJvm]<br>suspend fun [fetchCellSignal](fetch-cell-signal.md)()<br>Example calls for fetching CellSignal and WifiSignal values from the Requests |
| [fetchCheckList](fetch-check-list.md) | [androidJvm]<br>suspend fun [fetchCheckList](fetch-check-list.md)() |
| [sendConfigServiceLog](send-config-service-log.md) | [androidJvm]<br>fun [sendConfigServiceLog](send-config-service-log.md)(enable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), maxFileCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), maxFileSize: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [sendFileOperation](send-file-operation.md) | [androidJvm]<br>fun [sendFileOperation](send-file-operation.md)(files: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), options: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), destination: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutMS: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
