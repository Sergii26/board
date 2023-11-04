package com.practice.board.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber


object Converters {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Long> {
        Timber.d("fromString: ${value}")
        val listType = object : TypeToken<ArrayList<Long>>() {}.type
        return  Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<Long>): String {
        Timber.d("fromList: ${list}")
        return Gson().toJson(list)
    }
}