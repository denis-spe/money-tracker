package com.example.moneytracker.models.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moneytracker.models.income.Income

@Entity(
    tableName = "Expense",
    foreignKeys = [
        ForeignKey(
            entity = Income::class,
            parentColumns = ["income_id"],
            childColumns = ["income_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class Expense(
    @PrimaryKey
    var expense_id: Int,
    @ColumnInfo
    var income_id: Int,
    @ColumnInfo
    val dayOfWeek: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val year: String,
    @ColumnInfo
    var expense: Double

)
