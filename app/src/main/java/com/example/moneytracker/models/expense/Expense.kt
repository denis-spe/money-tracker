package com.example.moneytracker.models.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moneytracker.models.income.Income

@Entity(
    tableName = "Expense"
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var expenseId: Int = 0,
    @ColumnInfo
    val dayOfWeek: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val year: String,
    @ColumnInfo
    var expense: Double,
    @ColumnInfo
    var description: String

)
