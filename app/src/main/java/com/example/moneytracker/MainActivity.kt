package com.example.moneytracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneytracker.charts.DonutChartInput
import com.example.moneytracker.charts.DonutPieChart
import com.example.moneytracker.models.AppDatabase
import com.example.moneytracker.models.income.IncomeRepository
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.ui.theme.MoneyTrackerTheme

class MainActivity : ComponentActivity() {
    private lateinit var incomeViewModel: IncomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = AppDatabase.getInstance(this).incomeDao()
        val incomeRepository = IncomeRepository(userDao)
        incomeViewModel = IncomeViewModel(incomeRepository, this)

        setContent {
            MoneyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(incomeViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoneyTrackerTheme {
    }
}