package com.example.moneytracker.models.debts

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DebtRepository(private val debtDao: DebtDao) {

    fun getAllDebtsForAYear(year: String): LiveData<List<Double>> {
        return debtDao.getAllDebtsForAYear(year)
    }

    fun getAllDescriptionsForAYear(year: String): LiveData<List<String>> {
        return debtDao.getAllDescriptionsForAYear(year)
    }

    suspend fun insertDebts(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        debt: Double,
        description: String,
    ) {
        val debts = Debts(
            dayOfWeek = dayOfWeek,
            day = day,
            month = month,
            year = year,
            debt = debt,
            description = description
        )
        withContext(Dispatchers.IO) {
            debtDao.insertDebts(debts)
        }
    }

    suspend fun deleteDebt(debts: Debts) {
        withContext(Dispatchers.IO) {
            debtDao.deleteDebts(debts)
        }
    }

}
