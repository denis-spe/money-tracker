package com.example.moneytracker.models.income

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeRepository(private val incomeDao: IncomeDao) {
    fun getAllEarned(): LiveData<List<Income>> {
        return incomeDao.getAllEarned()
    }

    fun getMonth(year: String): LiveData<List<String>> {
        return incomeDao.getMonth(year)
    }

    fun getMeanForEarningAYear(year: String): LiveData<Double> {
        return incomeDao.getMeanForEarningAYear(year)
    }

    fun getMinForEarningAYear(year: String): LiveData<Double> {
        return incomeDao.getMinForEarningAYear(year)
    }

    fun getMaxForEarningAYear(year: String): LiveData<Double> {
        return incomeDao.getMaxForEarningAYear(year)
    }

    fun getTotalEarnedAYear(year: String): LiveData<Double> {
        return incomeDao.totalEarnedAYear(year)
    }

    fun getTotalEarnedAMonth(month: String, year: String): LiveData<Double> {
        return incomeDao.totalEarnedAMonth(month, year)
    }

    fun getTotalEarnedADay(day: String, month: String, year: String): LiveData<Double> {
        return incomeDao.totalEarnedADay(day, month, year)
    }

    fun getUniqueYear(): LiveData<List<String>> {
        return incomeDao.getUniqueYear()
    }

    suspend fun insertData(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        description: String,
        earned: Double
    ) {
        val user = Income(
            dayOfWeek = dayOfWeek,
            day = day,
            month = month,
            year = year,
            description = description,
            earned = earned
        )
        withContext(Dispatchers.IO) {
            incomeDao.insertUser(user)
        }
    }

    suspend fun deleteUser(user: Income) {
        withContext(Dispatchers.IO) {
            incomeDao.deleteUser(user)
        }
    }

    // Other repository methods...
}
