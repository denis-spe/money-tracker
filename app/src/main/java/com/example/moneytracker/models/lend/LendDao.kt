package com.example.moneytracker.models.lend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytracker.models.lend.Lend

@Dao
interface LendDao {
    @Query("SELECT lend FROM Lend WHERE year = :year")
    fun getAllLendingForAYear(year: String): LiveData<List<Double>>

    @Query("SELECT description FROM Lend WHERE year = :year")
    fun getAllDescriptionsForAYear(year: String): LiveData<List<String>>

    @Insert
    fun insertLend(lend: Lend)

    @Delete
    fun deleteLend(lend: Lend)
}