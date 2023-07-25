package com.example.nanoclientkotlin.dataRemote


import androidx.annotation.StringRes
import com.example.nanoclientkotlin.R

/**
 * A data class to represent the information presented in the dog card
 */
data class Item(
    //@DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    //val age: Int,
    @StringRes val description: Int
)

val items = listOf(
    Item( R.string.item_name_1, R.string.item_description_1),
    Item(R.string.item_name_2, R.string.item_description_2)
)
