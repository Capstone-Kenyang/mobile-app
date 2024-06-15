package com.example.kenyang.data.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "order_table")
@Parcelize
data class Order(
    @PrimaryKey val id: String,
    val menu: Menu,
    var isComplete: Boolean = false,
    val isDonation: Boolean = false
) : Parcelable
