package com.example.moneytracker.models.expense

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseRepository: ExpenseRepository,
                       @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    private val _expenseLiveData = MutableLiveData<List<Expense>>()
    val expenseLiveData: LiveData<List<Expense>> get() = _expenseLiveData

    private val _liveStringData = MutableLiveData<List<String>>()
    val liveStringData: LiveData<List<String>> get() = _liveStringData

    // Get live expense a year
    private val _liveExpenseAYear = MutableLiveData<Double>()
    val liveExpenseAYear: MutableLiveData<Double> get() = _liveExpenseAYear

    // Get live expense a month
    private val _liveExpenseAMonth = MutableLiveData<Double>()
    val liveExpenseAMonth: MutableLiveData<Double> get() = _liveExpenseAMonth

    // Get live expense a day
    private val _liveExpenseADay = MutableLiveData<Double>()
    val liveExpenseADay: MutableLiveData<Double> get() = _liveExpenseADay

    // Get the live mean of expense a year.
    private val _liveMeanForExpenseAYear = MutableLiveData<Double>()
    val liveMeanForExpenseAYear: MutableLiveData<Double> get() = _liveMeanForExpenseAYear

    // Get the live min of expense a year.
    private val _liveMinForExpenseAYear = MutableLiveData<Double>()
    val liveMinForExpenseAYear: MutableLiveData<Double> get() = _liveMinForExpenseAYear

    // Get the live max of expense a year.
    private val _liveMaxForExpenseAYear = MutableLiveData<Double>()
    val liveMaxForExpenseAYear: MutableLiveData<Double> get() = _liveMaxForExpenseAYear

    fun getMeanForExpenseAYear(year: String){
        expenseRepository.getMeanForExpenseAYear(year).observeForever{ mean ->
            liveMeanForExpenseAYear.value = mean
        }
    }

    fun getMinForExpenseAYear(year: String){
        expenseRepository.getMinForExpenseAYear(year).observeForever{ min ->
            liveMinForExpenseAYear.value = min
        }
    }

    fun getMaxForExpenseAYear(year: String){
        expenseRepository.getMaxForExpenseAYear(year).observeForever{ max ->
            liveMeanForExpenseAYear.value = max
        }
    }

    fun getAllExpense() {
        expenseRepository.getAllExpense().observeForever { expense ->
            _expenseLiveData.value = expense
        }
    }

    fun getMonth(year: String) {
        expenseRepository.getMonth(year).observeForever { month ->
            _liveStringData.value = month
        }
    }

    fun getUniqueYear() {
        expenseRepository.getUniqueYear().observeForever { year ->
            _liveStringData.value = year
        }
    }

    fun getTotalExpenseAYear(year: String){
        expenseRepository.getTotalExpenseAYear(year).observeForever{ totalExpense ->
            _liveExpenseAYear.value = totalExpense
        }
    }

    fun getTotalExpenseAMonth(month: String, year: String){
        expenseRepository.getTotalExpenseAMonth(month, year).observeForever{ totalEarned ->
            _liveExpenseAMonth.value = totalEarned
        }
    }

    fun getTotalExpenseADay(day: String, month: String, year: String){
        expenseRepository.getTotalExpenseADay(day, month, year).observeForever{ totalEarned ->
            _liveExpenseADay.value = totalEarned
        }
    }

    // Other ViewModel methods...
    fun insertExpense(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        expense: Double,
        description: String,
    ) {
        viewModelScope.launch {
            expenseRepository.insertExpense(
                dayOfWeek = dayOfWeek,
                day = day,
                month = month,
                year = year,
                expense = expense,
                description = description
            )
        }
    }

    fun deleteExpense(expense: Expense){
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }
}