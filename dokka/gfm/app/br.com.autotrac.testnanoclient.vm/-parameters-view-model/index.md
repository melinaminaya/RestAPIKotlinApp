//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[ParametersViewModel](index.md)

# ParametersViewModel

[androidJvm]\
class [ParametersViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [ParametersViewModel](-parameters-view-model.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [fetchParameters](fetch-parameters.md) | [androidJvm]<br>suspend fun [fetchParameters](fetch-parameters.md)() |
| [getParameterLiveData](get-parameter-live-data.md) | [androidJvm]<br>fun [getParameterLiveData](get-parameter-live-data.md)(parameter: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
| [setParam](set-param.md) | [androidJvm]<br>fun [setParam](set-param.md)(param: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [validateSelection](validate-selection.md) | [androidJvm]<br>suspend fun [validateSelection](validate-selection.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
