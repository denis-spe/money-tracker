package com.example.moneytracker.models

import androidx.lifecycle.LiveData
import com.example.datastore1.models.EarnedDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarnedRepository(private val earnedDao: EarnedDao) {
    fun getAllEarned(): LiveData<List<Earned>> {
        return earnedDao.getAllEarned()
    }

    fun getMonth(year: String): LiveData<List<String>> {
        return earnedDao.getMonth(year)
    }

    fun getTotalEarnedAYear(): LiveData<List<Double>>{
        return earnedDao.totalEarnedAYear()
    }

    fun getUniqueYear(): LiveData<List<String>> {
        return earnedDao.getUniqueYear()
    }

    suspend fun insertUser(dayOfWeek: String,
                           day: String,
                           month: String,
                           year: String,
                           earned: Double){
        val user = Earned(
            dayOfWeek = dayOfWeek,
            day = day,
            month = month,
            year = year,
            earned = earned
        )
        withContext(Dispatchers.IO) {
            earnedDao.insertUser(user)
        }
    }

    suspend fun deleteUser(user: Earned){
        withContext(Dispatchers.IO) {
            earnedDao.deleteUser(user)
        }
    }

    // Other repository methods...
}
