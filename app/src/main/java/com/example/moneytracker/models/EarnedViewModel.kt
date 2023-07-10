package com.example.moneytracker.models

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EarnedViewModel(private val earnedRepository: EarnedRepository,
                      @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    private val _earnedLiveData = MutableLiveData<List<Earned>>()
    val earnedLiveData: LiveData<List<Earned>> get() = _earnedLiveData

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

    fun getAllEarned() {
        earnedRepository.getAllEarned().observeForever { earned ->
            _earnedLiveData.value = earned
        }
    }

    fun getMonth(year: String) {
        earnedRepository.getMonth(year).observeForever { month ->
            _liveStringData.value = month
        }
    }

    fun getUniqueYear() {
        earnedRepository.getUniqueYear().observeForever { year ->
            _liveStringData.value = year
        }
    }

    fun getTotalEarnedAYear(year: String){
        earnedRepository.getTotalEarnedAYear(year).observeForever{totalEarned ->
            _liveEarnedAYear.value = totalEarned
        }
    }

    fun getTotalEarnedAMonth(month: String, year: String){
        earnedRepository.getTotalEarnedAMonth(month, year).observeForever{totalEarned ->
            _liveEarnedAMonth.value = totalEarned
        }
    }

    fun getTotalEarnedADay(day: String, month: String, year: String){
        earnedRepository.getTotalEarnedADay(day, month, year).observeForever{totalEarned ->
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
            earnedRepository.insertUser(
                dayOfWeek = dayOfWeek,
                day = day,
                month = month,
                year = year,
                earned)
        }
    }

    fun delete(earned: Earned){
        viewModelScope.launch {
            earnedRepository.deleteUser(earned)
        }
    }
}