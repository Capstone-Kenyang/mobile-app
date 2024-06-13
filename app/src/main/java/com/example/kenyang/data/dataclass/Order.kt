package com.example.kenyang.data.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: String,
    val imageId: Int,
    val menu: Menu,
    val status: String
) : Parcelable
