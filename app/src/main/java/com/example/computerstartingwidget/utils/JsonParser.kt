package com.example.computerstartingwidget.utils

import com.google.gson.GsonBuilder

class JsonParser {

    fun <T> parseJson(jsonStr: String?, clazz: Class<T>?): T? {
        if (jsonStr == null) return null
        val builder = GsonBuilder()
        val gson = builder.create()

        return gson.fromJson(jsonStr, clazz)
    }

}