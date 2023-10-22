package com.example.moneytracker.models.lend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.lend.Lend

@Dao
interface LendDao {
    @Query("SELECT * FROM Lend")
    fun getAllLending(): LiveData<List<Lend>>

    @Query("SELECT DISTINCT month FROM Lend WHERE year = :year")
    fun getMonth(year: String): LiveData<List<String>>

    @Query("SELECT DISTINCT year  FROM Lend ORDER BY year")
    fun getUniqueYear(): LiveData<List<String>>

    @Query("SELECT sum(lend)  FROM Lend WHERE year = :year")
    fun totalLendAYear(year: String): LiveData<Double>

    @Query("SELECT AVG(lend)  FROM Lend WHERE year = :year")
    fun getMeanForLendingAYear(year: String): LiveData<Double>

    @Query("SELECT MIN(lend)  FROM Lend WHERE year = :year")
    fun getMinForLendingAYear(year: String): LiveData<Double>

    @Query("SELECT Max(lend)  FROM Lend WHERE year = :year")
    fun getMaxForLendingAYear(year: String): LiveData<Double>

    @Query("SELECT sum(lend)  FROM Lend  WHERE year = :year AND month = :month")
    fun totalLendAMonth(month: String, year: String): LiveData<Double>

    @Query("SELECT sum(lend)  FROM Lend WHERE year = :year AND month = :month AND day = :day")
    fun totalLendADay(day: String, month: String, year: String): LiveData<Double>

    @Insert
    fun insertLend(lend: Lend)

    @Delete
    fun deleteLend(lend: Lend)
}