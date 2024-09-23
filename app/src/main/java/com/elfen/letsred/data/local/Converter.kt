package com.elfen.letsred.data.local

import androidx.room.TypeConverter
import com.elfen.letsred.data.local.models.LocalImage
import com.elfen.letsred.data.local.models.LocalImageSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class Converter {
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val imageSourceAdapter = moshi.adapter(LocalImageSource::class.java)
    private val listImageSourceAdapter = moshi.adapter<List<LocalImageSource>>(
        Types.newParameterizedType(
            List::class.java,
            LocalImageSource::class.java
        )
    )
    private val listImageAdapter = moshi.adapter<List<LocalImage>>(
        Types.newParameterizedType(
            List::class.java,
            LocalImage::class.java
        )
    )
    private val listStringAdapter = moshi.adapter<List<String>>(
        Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
    )


    @TypeConverter
    fun fromJsonToImageSource(json: String?): LocalImageSource? {
        return imageSourceAdapter.fromJson(json ?: return null)!!
    }

    @TypeConverter
    fun fromImageSourceToJson(imageSource: LocalImageSource?): String? {
        return imageSourceAdapter.toJson(imageSource ?: return null)
    }


    @TypeConverter
    fun fromListImageSourceToJson(imageSources: List<LocalImageSource>?): String? {
        return listImageSourceAdapter.toJson(imageSources ?: return null)
    }

    @TypeConverter
    fun fromJsonToListImageSource(json: String?): List<LocalImageSource>? {
        return listImageSourceAdapter.fromJson(json ?: return null)!!
    }


    @TypeConverter
    fun fromListImageToJson(imageList: List<LocalImage>?): String? {
        return listImageAdapter.toJson(imageList ?: return null)
    }

    @TypeConverter
    fun fromJsonToListImage(json: String?): List<LocalImage>? {
        return listImageAdapter.fromJson(json ?: return null)!!
    }


    @TypeConverter
    fun fromListStringToJson(stringList: List<String>?): String? {
        return listStringAdapter.toJson(stringList ?: return null)
    }

    @TypeConverter
    fun fromJsonToListString(json: String?): List<String>? {
        return listStringAdapter.fromJson(json ?: return null)
    }
}

