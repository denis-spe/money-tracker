package com.example.moneytracker.models.income

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.models.income.Income
import com.example.moneytracker.models.income.IncomeRepository
import kotlinx.coroutines.launch

class IncomeViewModel(private val incomeRepository: IncomeRepository,
                      @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    private val _incomeLiveData = MutableLiveData<List<Income>>()
    val incomeLiveData: LiveData<List<Income>> get() = _incomeLiveData

    private val _liveStringData = MutableLiveData<List<String>>()
    val liveStringData: LiveData<List<String>> get() = _liveStringData

    // Get live earning a year
    private val _liveEarnedAYear = MutableLiveData<Double>()
    val liveEarnedAYear: MutableLiveData<Double> get() = _liveEarnedAYear

    // Get live earning a month
    private val _liveEarnedAMonth = MutableLiveData<Double>()
    val liveEarnedAMonth: MutableLiveData<Double> get() = _liveEarnedAMonth

    // Get live earning a day
    private val _liveEarnedADay = MutableLiveData<Double>()
    val liveEarnedADay: MutableLiveData<Double> get() = _liveEarnedADay

    // Get the live mean of earning a year.
    private val _liveMeanForEarningAYear = MutableLiveData<Double>()
    val liveMeanForEarningAYear: MutableLiveData<Double> get() = _liveMeanForEarningAYear

    // Get the live min of earning a year.
    private val _liveMinForEarningAYear = MutableLiveData<Double>()
    val liveMinForEarningAYear: MutableLiveData<Double> get() = _liveMinForEarningAYear

    // Get the live max of earning a year.
    private val _liveMaxForEarningAYear = MutableLiveData<Double>()
    val liveMaxForEarningAYear: MutableLiveData<Double> get() = _liveMaxForEarningAYear

    fun getMeanForEarningAYear(year: String){
        incomeRepository.getMeanForEarningAYear(year).observeForever{ mean ->
            liveMeanForEarningAYear.value = mean
        }
    }

    fun getMinForEarningAYear(year: String){
        incomeRepository.getMinForEarningAYear(year).observeForever{ min ->
            liveMinForEarningAYear.value = min
        }
    }

    fun getMaxForEarningAYear(year: String){
        incomeRepository.getMaxForEarningAYear(year).observeForever{ max ->
            liveMeanForEarningAYear.value = max
        }
    }

    fun getAllEarned() {
        incomeRepository.getAllEarned().observeForever { earned ->
            _incomeLiveData.value = earned
        }
    }

    fun getMonth(year: String) {
        incomeRepository.getMonth(year).observeForever { month ->
            _liveStringData.value = month
        }
    }

    fun getUniqueYear() {
        incomeRepository.getUniqueYear().observeForever { year ->
            _liveStringData.value = year
        }
    }

    fun getTotalEarnedAYear(year: String){
        incomeRepository.getTotalEarnedAYear(year).observeForever{ totalEarned ->
            _liveEarnedAYear.value = totalEarned
        }
    }

    fun getTotalEarnedAMonth(month: String, year: String){
        incomeRepository.getTotalEarnedAMonth(month, year).observeForever{ totalEarned ->
            _liveEarnedAMonth.value = totalEarned
        }
    }

    fun getTotalEarnedADay(day: String, month: String, year: String){
        incomeRepository.getTotalEarnedADay(day, month, year).observeForever{ totalEarned ->
            _liveEarnedADay.value = totalEarned
        }
    }

    // Other ViewModel methods...
    fun insertUser(dayOfWeek: String,
                   day: String,
                   month: String,
                   year: String,
                   earned: Double) {
        viewModelScope.launch {
            incomeRepository.insertUser(
                dayOfWeek = dayOfWeek,
                day = day,
                month = month,
                year = year,
                earned)
        }
    }

    fun delete(income: Income){
        viewModelScope.launch {
            incomeRepository.deleteUser(income)
        }
    }
}