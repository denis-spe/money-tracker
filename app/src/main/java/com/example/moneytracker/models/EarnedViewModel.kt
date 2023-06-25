package com.example.moneytracker.models

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.models.Earned
import com.example.moneytracker.models.EarnedRepository
import kotlinx.coroutines.launch

class EarnedViewModel(private val earnedRepository: EarnedRepository,
                      @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    private val _earnedLiveData = MutableLiveData<List<Earned>>()
    val earnedLiveData: LiveData<List<Earned>> get() = _earnedLiveData

    private val _liveStringData = MutableLiveData<List<String>>()
    val liveStringData: LiveData<List<String>> get() = _liveStringData

    private val _liveDoubleData = MutableLiveData<List<Double>>()
    val liveDoubleData: MutableLiveData<List<Double>> get() = _liveDoubleData

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

    fun getTotalEarnedAYear(){
        earnedRepository.getTotalEarnedAYear().observeForever{totalEarned ->
            _liveDoubleData.value = totalEarned
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