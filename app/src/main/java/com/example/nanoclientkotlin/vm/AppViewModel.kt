package com.example.nanoclientkotlin.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel:ViewModel() {
    var isApiOn by mutableStateOf(false)
}