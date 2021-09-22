package com.example.computerstartingwidget.room

import androidx.room.*


@Dao
interface MacAddressDao {

    @Query("SELECT * FROM macaddress")
    fun getAll(): List<MacAddress?>?

    @Query("SELECT * FROM macaddress WHERE id = :id")
    fun getById(id: Int): MacAddress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: MacAddress?)

    @Update
    fun update(employee: MacAddress?)

    @Delete
    fun delete(employee: MacAddress?)
}