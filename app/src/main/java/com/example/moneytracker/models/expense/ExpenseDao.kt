package com.example.moneytracker.models.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.income.Income

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense")
    fun getAllEarned(): LiveData<List<Expense>>

    @Query("SELECT DISTINCT month FROM Expense WHERE year = :year")
    fun getMonth(year: String): LiveData<List<String>>

    @Query("SELECT DISTINCT year  FROM Expense ORDER BY year")
    fun getUniqueYear(): LiveData<List<String>>

    @Query("SELECT sum(expense)  FROM Expense WHERE year = :year")
    fun totalEarnedAYear(year: String): LiveData<Double>

    @Query("SELECT AVG(expense)  FROM Expense WHERE year = :year")
    fun getMeanForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT MIN(expense)  FROM Expense WHERE year = :year")
    fun getMinForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT Max(expense)  FROM Expense WHERE year = :year")
    fun getMaxForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT sum(expense)  FROM Expense  WHERE year = :year AND month = :month")
    fun totalEarnedAMonth(month: String, year: String): LiveData<Double>

    @Query("SELECT sum(expense)  FROM Expense WHERE year = :year AND month = :month AND day = :day")
    fun totalEarnedADay(day: String, month: String, year: String): LiveData<Double>

    @Insert
    fun insertExpense(expense: Expense)

    @Delete
    fun deleteExpense(expense: Expense)
}