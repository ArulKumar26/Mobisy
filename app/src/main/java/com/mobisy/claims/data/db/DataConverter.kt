package com.mobisy.claims.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobisy.claims.data.model.ResultClaimTypeOption

class DataConverter {
    private var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): ArrayList<ResultClaimTypeOption?>? {
        if (data == null) return null
        val listType = object : TypeToken<List<ResultClaimTypeOption>>() {}.type
        return gson.fromJson<ArrayList<ResultClaimTypeOption?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(resultClaimTypeOption: ArrayList<ResultClaimTypeOption>): String? =
        gson.toJson(resultClaimTypeOption)
}