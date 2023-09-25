package com.example.nanoclientkotlin.vm

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class AppViewModelStore: ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()

    fun customGetViewModelStore(): ViewModelStore {
        return viewModelStore
    }
}