package com.example.moneytracker.models.income

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.income.Income

@Dao
interface IncomeDao {
    @Query("SELECT * FROM Income")
    fun getAllEarned(): LiveData<List<Income>>

    @Query("SELECT DISTINCT month FROM Income WHERE year = :year")
    fun getMonth(year: String): LiveData<List<String>>

    @Query("SELECT DISTINCT year  FROM Income ORDER BY year")
    fun getUniqueYear(): LiveData<List<String>>

    @Query("SELECT sum(earned)  FROM Income WHERE year = :year")
    fun totalEarnedAYear(year: String): LiveData<Double>

    @Query("SELECT AVG(earned)  FROM Income WHERE year = :year")
    fun getMeanForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT MIN(earned)  FROM Income WHERE year = :year")
    fun getMinForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT Max(earned)  FROM Income WHERE year = :year")
    fun getMaxForEarningAYear(year: String): LiveData<Double>

    @Query("SELECT sum(earned)  FROM Income  WHERE year = :year AND month = :month")
    fun totalEarnedAMonth(month: String, year: String): LiveData<Double>

    @Query("SELECT sum(earned)  FROM Income WHERE year = :year AND month = :month AND day = :day")
    fun totalEarnedADay(day: String, month: String, year: String): LiveData<Double>

    @Insert
    fun insertUser(income: Income)

    @Delete
    fun deleteUser(income: Income)
}