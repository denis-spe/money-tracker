package com.example.moneytracker.models.debts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DebtDao {

    @Query("SELECT debt FROM Debts WHERE year = :year")
    fun getAllDebtsForAYear(year: String): LiveData<List<Double>>

    @Query("SELECT description FROM Debts WHERE year = :year")
    fun getAllDescriptionsForAYear(year: String): LiveData<List<String>>

    @Insert
    fun insertDebts(debts: Debts)

    @Delete
    fun deleteDebts(debts: Debts)
}