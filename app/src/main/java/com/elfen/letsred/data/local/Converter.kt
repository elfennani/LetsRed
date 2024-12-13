package com.elfen.letsred.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class Converter {
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val listStringAdapter = moshi.adapter<List<String>>(
        Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
    )

    @TypeConverter
    fun fromListStringToJson(stringList: List<String>?): String? {
        return listStringAdapter.toJson(stringList ?: return null)
    }

    @TypeConverter
    fun fromJsonToListString(json: String?): List<String>? {
        return listStringAdapter.fromJson(json ?: return null)
    }
}

