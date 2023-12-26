package com.example.moneytracker.models.lend

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LendViewModel(
    private val lendRepository: LendRepository,
    @SuppressLint("StaticFieldLeak") private val applicationContext: Context
) : ViewModel() {

    // Get live expense a year
    private val _liveLendAYear = MutableLiveData<List<Double>>()
    val liveLendAYear: MutableLiveData<List<Double>> get() = _liveLendAYear

    private val _liveDescAYear = MutableLiveData<List<String>>()
    val liveDescAYear: MutableLiveData<List<String>> get() = _liveDescAYear

    fun getAllDescriptionForAYear(year: String) {
        lendRepository.getAllDescriptionsForAYear(year).observeForever { description ->
            _liveDescAYear.value = description
        }
    }

    fun getAllLendingForAYear(year: String) {
        lendRepository.getAllLendingForAYear(year).observeForever { lend ->
            _liveLendAYear.value = lend
        }
    }

    // Other ViewModel methods...
    fun insertLend(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        lend: Double,
        description: String,
    ) {
        viewModelScope.launch {
            lendRepository.insertLend(
                dayOfWeek = dayOfWeek,
                day = day,
                month = month,
                year = year,
                lend = lend,
                description = description
            )
        }
    }

    fun deleteLend(lend: Lend) {
        viewModelScope.launch {
            lendRepository.deleteLend(lend)
        }
    }
}