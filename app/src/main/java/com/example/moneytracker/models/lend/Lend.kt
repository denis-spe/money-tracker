package com.example.moneytracker.models.lend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Lend"
)
data class Lend(
    @PrimaryKey(autoGenerate = true)
    var lendId: Int = 0,
    @ColumnInfo
    val dayOfWeek: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val year: String,
    @ColumnInfo
    var lend: Double,
    @ColumnInfo
    var description: String

)
