package com.example.computerstartingwidget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MacAddress(
    @PrimaryKey
    val id: Int,
    val address: String
)