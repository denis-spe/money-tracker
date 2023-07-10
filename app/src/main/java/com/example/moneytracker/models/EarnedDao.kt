package com.example.datastore1.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.Earned
import java.time.Year

@Dao
interface EarnedDao {
    @Query("SELECT * FROM Earned")
    fun getAllEarned(): LiveData<List<Earned>>

    @Query("SELECT DISTINCT month FROM Earned WHERE year = :year")
    fun getMonth(year: String): LiveData<List<String>>

    @Query("SELECT DISTINCT year  FROM Earned ORDER BY year")
    fun getUniqueYear(): LiveData<List<String>>

    @Query("SELECT sum(earned)  FROM Earned WHERE year = :year")
    fun totalEarnedAYear(year: String): LiveData<Double>

    @Query("SELECT sum(earned)  FROM Earned  WHERE year = :year AND month = :month")
    fun totalEarnedAMonth(month: String, year: String): LiveData<Double>

    @Query("SELECT sum(earned)  FROM Earned WHERE year = :year AND month = :month AND day = :day")
    fun totalEarnedADay(day: String, month: String, year: String): LiveData<Double>

    @Insert
    fun insertUser(earned: Earned)

    @Delete
    fun deleteUser(earned: Earned)
}