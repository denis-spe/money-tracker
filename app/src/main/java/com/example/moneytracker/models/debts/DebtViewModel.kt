package com.example.moneytracker.models.debts

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DebtViewModel(private val debtRepository: DebtRepository,
                    @SuppressLint("StaticFieldLeak") private val applicationContext: Context) : ViewModel() {

    // Get live expense a year
    private val _liveDebtsAYear = MutableLiveData<List<Double>>()
    val liveDebtsAYear: MutableLiveData<List<Double>> get() = _liveDebtsAYear

    private val _liveDescAYear = MutableLiveData<List<String>>()
    val liveDescAYear: MutableLiveData<List<String>> get() = _liveDescAYear

    fun getAllDescriptionForAYear(year: String){
        debtRepository.getAllDescriptionsForAYear(year).observeForever{ description ->
            _liveDescAYear.value = description
        }
    }

    fun getAllDebtsForAYear(year: String){
        debtRepository.getAllDebtsForAYear(year).observeForever{ debts ->
            _liveDebtsAYear.value = debts
        }
    }

    // Other ViewModel methods...
    fun insertDebt(
        dayOfWeek: String,
        day: String,
        month: String,
        year: String,
        debt: Double,
        description: String,
    ) {
        viewModelScope.launch {
            debtRepository.insertDebts(
                dayOfWeek = dayOfWeek,
                day = day,
                month = month,
                year = year,
                debt = debt,
                description = description
            )
        }
    }

    fun deleteDebts(debt: Debts){
        viewModelScope.launch {
            debtRepository.deleteDebt(debt)
        }
    }
}