package com.example.moneytracker.models.lend

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LendViewModel(private val lendRepository: LendRepository,
                    @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    private val _lendLiveData = MutableLiveData<List<Lend>>()
    val lendLiveData: LiveData<List<Lend>> get() = _lendLiveData

    private val _liveStringData = MutableLiveData<List<String>>()
    val liveStringData: LiveData<List<String>> get() = _liveStringData

    // Get live expense a year
    private val _liveLendAYear = MutableLiveData<Double>()
    val liveLendAYear: MutableLiveData<Double> get() = _liveLendAYear

    // Get live expense a month
    private val _liveLendAMonth = MutableLiveData<Double>()
    val liveLendAMonth: MutableLiveData<Double> get() = _liveLendAMonth

    // Get live expense a day
    private val _liveLendADay = MutableLiveData<Double>()
    val liveLendADay: MutableLiveData<Double> get() = _liveLendADay

    // Get the live mean of expense a year.
    private val _liveMeanForLendAYear = MutableLiveData<Double>()
    val liveMeanForLendAYear: MutableLiveData<Double> get() = _liveMeanForLendAYear

    // Get the live min of expense a year.
    private val _liveMinForLendAYear = MutableLiveData<Double>()
    val liveMinForLendAYear: MutableLiveData<Double> get() = _liveMinForLendAYear

    // Get the live max of expense a year.
    private val _liveMaxForLendAYear = MutableLiveData<Double>()
    val liveMaxForLendAYear: MutableLiveData<Double> get() = _liveMaxForLendAYear

    fun getMeanForLendingAYear(year: String){
        lendRepository.getMeanForLendingAYear(year).observeForever{ mean ->
            liveMeanForLendAYear.value = mean
        }
    }

    fun getMinForLendingAYear(year: String){
        lendRepository.getMinForLendingAYear(year).observeForever{ min ->
            liveMinForLendAYear.value = min
        }
    }

    fun getMaxForLendingAYear(year: String){
        lendRepository.getMaxForLendingAYear(year).observeForever{ max ->
            liveMeanForLendAYear.value = max
        }
    }

    fun getAllExpense() {
        lendRepository.getAllLending().observeForever { lend ->
            _lendLiveData.value = lend
        }
    }

    fun getMonth(year: String) {
        lendRepository.getMonth(year).observeForever { month ->
            _liveStringData.value = month
        }
    }

    fun getUniqueYear() {
        lendRepository.getUniqueYear().observeForever { year ->
            _liveStringData.value = year
        }
    }

    fun getTotalExpenseAYear(year: String){
        lendRepository.getTotalLendAYear(year).observeForever{ totalLend ->
            _liveLendAYear.value = totalLend
        }
    }

    fun getTotalExpenseAMonth(month: String, year: String){
        lendRepository.getTotalLendAMonth(month, year).observeForever{ totalLend ->
            _liveLendAMonth.value = totalLend
        }
    }

    fun getTotalExpenseADay(day: String, month: String, year: String){
        lendRepository.getTotalLendADay(day, month, year).observeForever{ totalLend ->
            _liveLendADay.value = totalLend
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

    fun deleteLend(lend: Lend){
        viewModelScope.launch {
            lendRepository.deleteLend(lend)
        }
    }
}