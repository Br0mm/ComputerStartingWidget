package com.example.computerstartingwidget.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MacAddress::class], version = 1)
abstract class MacAddressDatabase : RoomDatabase() {
    abstract fun macAddressDao() : MacAddressDao?
}