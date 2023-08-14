package com.example.moneytracker.models.income

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Income")
data class Income (
    @PrimaryKey(autoGenerate = true)
    val income_id: Int = 0,
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
