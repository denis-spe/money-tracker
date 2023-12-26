package com.example.moneytracker.models.lend

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LendRepository(private val lendDao: LendDao) {
    fun getAllLendingForAYear(year: String): LiveData<List<Double>> {
        return lendDao.getAllLendingForAYear(year)
    }

    fun getAllDescriptionsForAYear(year: String): LiveData<List<String>> {
        return lendDao.getAllDescriptionsForAYear(year)
    }

    suspend fun insertLend(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        lend: Double,
        description: String,
    ) {
        val lend = Lend(
            dayOfWeek = dayOfWeek,
            day = day,
            month = month,
            year = year,
            lend = lend,
            description = description
        )
        withContext(Dispatchers.IO) {
            lendDao.insertLend(lend)
        }
    }

    suspend fun deleteLend(lend: Lend) {
        withContext(Dispatchers.IO) {
            lendDao.deleteLend(lend)
        }
    }

}
