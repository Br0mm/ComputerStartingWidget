package com.example.computerstartingwidget.activity

import android.app.Application
import androidx.room.Room
import com.example.computerstartingwidget.room.MacAddressDatabase


class App : Application() {

    companion object {
        var instance: App? = null
    }

    private var database: MacAddressDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, MacAddressDatabase::class.java, "database").build()
    }

    fun getInstance(): App? {
        return instance
    }

    fun getDatabase(): MacAddressDatabase? {
        return database
    }
}