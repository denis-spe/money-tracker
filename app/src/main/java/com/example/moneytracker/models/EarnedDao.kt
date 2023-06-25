package com.example.datastore1.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.Earned

@Dao
interface EarnedDao {
    @Query("SELECT * FROM Earned")
    fun getAllEarned(): LiveData<List<Earned>>

    @Query("SELECT DISTINCT month FROM Earned WHERE year = :year")
    fun getMonth(year: String): LiveData<List<String>>

    @Query("SELECT DISTINCT year  FROM Earned ORDER BY year")
    fun getUniqueYear(): LiveData<List<String>>

    @Query("SELECT SUM(earned)  FROM Earned GROUP BY year")
    fun totalEarnedAYear(): LiveData<List<Double>>

    @Insert
    fun insertUser(earned: Earned)

    @Delete
    fun deleteUser(earned: Earned)
}