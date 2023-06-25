package com.example.moneytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Earned")
data class Earned (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val dayOfWeek: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val year: String,
    @ColumnInfo
    val earned: Double
)
