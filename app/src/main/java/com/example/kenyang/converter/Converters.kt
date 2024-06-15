package com.example.kenyang.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.kenyang.data.dataclass.Menu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromMenu(menu: Menu): String = Gson().toJson(menu)

    @TypeConverter
    fun toMenu(menuString: String): Menu = Gson().fromJson(menuString, Menu::class.java)
}