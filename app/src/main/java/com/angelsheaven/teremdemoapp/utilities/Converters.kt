package com.angelsheaven.teremdemoapp.utilities

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters{

    @TypeConverter
    fun fromKidsValueToList(kidsList:List<Int>?):String?{
        kidsList?.run {
            return Gson().toJson(this)
        }
        return null
    }

    @TypeConverter
    fun jsonToKidsValues(value:String?):List<Int>?{
        val objects:Array<Int>? = Gson().fromJson(value,Array<Int>::class.java)
        return objects?.toList()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date?{
        return if(value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time
    }

}
