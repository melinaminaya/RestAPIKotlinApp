//[app](../../../index.md)/[br.com.autotrac.testnanoclient.vm](../index.md)/[FilePickerViewModel](index.md)

# FilePickerViewModel

[androidJvm]\
class [FilePickerViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [FilePickerViewModel](-file-picker-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [fileProcessed](file-processed.md) | [androidJvm]<br>val [fileProcessed](file-processed.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [fileProcessedString](file-processed-string.md) | [androidJvm]<br>val [fileProcessedString](file-processed-string.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)&gt; |
| [permissionState](permission-state.md) | [androidJvm]<br>val [permissionState](permission-state.md): StateFlow&lt;[PermissionState](../-permission-state/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [processFile](process-file.md) | [androidJvm]<br>suspend fun [processFile](process-file.md)(selectedFileUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)?, contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html)): [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)? |
| [updatePermissionState](update-permission-state.md) | [androidJvm]<br>fun [updatePermissionState](update-permission-state.md)(state: [PermissionState](../-permission-state/index.md)) |

## Inherited functions

| Name | Summary |
|---|---|
| [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-reset-database-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
