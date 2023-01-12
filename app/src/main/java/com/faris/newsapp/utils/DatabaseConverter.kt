package com.faris.newsapp.utils

import androidx.room.TypeConverter
import com.faris.newsapp.models.PopularMenu

class DatabaseConverter {
    @TypeConverter
    fun fromPopularMenu(popularMenu: PopularMenu): String {
        return popularMenu.name
    }

    @TypeConverter
    fun toPopularMenu(popularMenu: String): PopularMenu {
        return PopularMenu.valueOf(popularMenu)
    }
}