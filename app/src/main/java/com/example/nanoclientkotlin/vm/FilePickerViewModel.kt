package com.example.nanoclientkotlin.vm

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class FilePickerViewModel : ViewModel() {
    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.NotRequested)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _fileProcessed = MutableStateFlow(false)
    val fileProcessed: StateFlow<Boolean> = _fileProcessed

    private val _fileProcessedString = MutableLiveData<Uri>()
    val fileProcessedString: LiveData<Uri> get() = _fileProcessedString

    fun updatePermissionState(state: PermissionState) {
        _permissionState.value = state
    }

    suspend fun processFile(selectedFileUri: Uri?, contentResolver: ContentResolver): Uri? {
//        return suspendCancellableCoroutine { continuation ->
//            viewModelScope.launch(Dispatchers.IO) {
//                val fileString = selectedFileUri?.let { uriToJsonString(contentResolver, it) }
//                if (fileString != null) {
//                    withContext(Dispatchers.Main) {
//                        _fileProcessed.value = true
//                        _fileProcessedString.value = fileString
//                    }
//                }
//                continuation.resume(fileString)
//            }
//        }
        _fileProcessed.value = true
        _fileProcessedString.value = selectedFileUri
        return _fileProcessedString.value
    }
    private suspend fun uriToJsonString(contentResolver: ContentResolver, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                val stringBuilder = StringBuilder()

                while (true) {
                    val chunk = reader.readLine() ?: break
                    stringBuilder.append(chunk)
                }

                reader.close()
                inputStream?.close()

                return@withContext stringBuilder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}
sealed class PermissionState {
    object NotRequested : PermissionState()
    object Granted : PermissionState()
    object Denied : PermissionState()
}

