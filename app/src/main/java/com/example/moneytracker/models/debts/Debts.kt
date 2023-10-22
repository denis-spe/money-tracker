package com.example.moneytracker.models.debts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Debts"
)
data class Debts(
    @PrimaryKey(autoGenerate = true)
    var debtId: Int = 0,
    @ColumnInfo
    val dayOfWeek: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val year: String,
    @ColumnInfo
    var debt: Double,
    @ColumnInfo
    var description: String

)
