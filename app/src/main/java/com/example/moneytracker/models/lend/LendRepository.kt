package com.example.moneytracker.models.lend

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LendRepository(private val lendDao: LendDao) {
    fun getAllLending(): LiveData<List<Lend>> {
        return lendDao.getAllLending()
    }

    fun getMonth(year: String): LiveData<List<String>> {
        return lendDao.getMonth(year)
    }

    fun getMeanForLendingAYear(year: String): LiveData<Double>{
        return lendDao.getMeanForLendingAYear(year)
    }

    fun getMinForLendingAYear(year: String): LiveData<Double>{
        return lendDao.getMinForLendingAYear(year)
    }

    fun getMaxForLendingAYear(year: String): LiveData<Double>{
        return lendDao.getMaxForLendingAYear(year)
    }

    fun getTotalLendAYear(year: String): LiveData<Double>{
        return lendDao.totalLendAYear(year)
    }

    fun getTotalLendAMonth(month: String, year: String): LiveData<Double>{
        return lendDao.totalLendAMonth(month, year)
    }

    fun getTotalLendADay(day: String, month: String, year: String): LiveData<Double>{
        return lendDao.totalLendADay(day, month, year)
    }

    fun getUniqueYear(): LiveData<List<String>> {
        return lendDao.getUniqueYear()
    }

    suspend fun insertLend(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        lend: Double,
        description: String,
    ){
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

    suspend fun deleteLend(lend: Lend){
        withContext(Dispatchers.IO) {
            lendDao.deleteLend(lend)
        }
    }

}
