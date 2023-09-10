package com.example.moneytracker.models.expense

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getAllExpense(): LiveData<List<Expense>> {
        return expenseDao.getAllEarned()
    }

    fun getMonth(year: String): LiveData<List<String>> {
        return expenseDao.getMonth(year)
    }

    fun getMeanForExpenseAYear(year: String): LiveData<Double>{
        return expenseDao.getMeanForEarningAYear(year)
    }

    fun getMinForExpenseAYear(year: String): LiveData<Double>{
        return expenseDao.getMinForEarningAYear(year)
    }

    fun getMaxForExpenseAYear(year: String): LiveData<Double>{
        return expenseDao.getMaxForEarningAYear(year)
    }

    fun getTotalExpenseAYear(year: String): LiveData<Double>{
        return expenseDao.totalEarnedAYear(year)
    }

    fun getTotalExpenseAMonth(month: String, year: String): LiveData<Double>{
        return expenseDao.totalEarnedAMonth(month, year)
    }

    fun getTotalExpenseADay(day: String, month: String, year: String): LiveData<Double>{
        return expenseDao.totalEarnedADay(day, month, year)
    }

    fun getUniqueYear(): LiveData<List<String>> {
        return expenseDao.getUniqueYear()
    }

    suspend fun insertExpense(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        expense: Double,
        description: String,
    ){
        val expense = Expense(
            dayOfWeek = dayOfWeek,
            day = day,
            month = month,
            year = year,
            expense = expense,
            description = description
        )
        withContext(Dispatchers.IO) {
            expenseDao.insertExpense(expense)
        }
    }

    suspend fun deleteExpense(expense: Expense){
        withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(expense)
        }
    }

}
