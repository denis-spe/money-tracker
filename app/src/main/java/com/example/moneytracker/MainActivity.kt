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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneytracker.charts.DonutChartInput
import com.example.moneytracker.charts.DonutPieChart
import com.example.moneytracker.models.AppDatabase
import com.example.moneytracker.models.expense.ExpenseDao
import com.example.moneytracker.models.expense.ExpenseRepository
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.income.IncomeDao
import com.example.moneytracker.models.income.IncomeRepository
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.ui.theme.MoneyTrackerTheme

class MainActivity : ComponentActivity() {
    // Instantiate view models
    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var expenseViewModel: ExpenseViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the dao
        val incomeDao: IncomeDao = AppDatabase.getInstance(this).incomeDao()
        val expenseDao: ExpenseDao = AppDatabase.getInstance(this).expenseDao()

        // Initialize the repositories
        val incomeRepository: IncomeRepository = IncomeRepository(incomeDao)
        val expenseRepository: ExpenseRepository = ExpenseRepository(expenseDao)

        // Assign view models
        incomeViewModel = IncomeViewModel(incomeRepository, this)
        expenseViewModel = ExpenseViewModel(expenseRepository, this)

        setContent {
            MoneyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        incomeViewModel,
                        expenseViewModel
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoneyTrackerTheme {
        // Instantiate view models
        lateinit var incomeViewModel: IncomeViewModel
        lateinit var expenseViewModel: ExpenseViewModel

        val context = LocalContext.current

        // Initialize the dao
        val incomeDao: IncomeDao = AppDatabase.getInstance(context).incomeDao()
        val expenseDao: ExpenseDao = AppDatabase.getInstance(context).expenseDao()

        // Initialize the repositories
        val incomeRepository: IncomeRepository = IncomeRepository(incomeDao)
        val expenseRepository: ExpenseRepository = ExpenseRepository(expenseDao)

        // Assign view models
        incomeViewModel = IncomeViewModel(incomeRepository, context)
        expenseViewModel = ExpenseViewModel(expenseRepository, context)

        App(
            incomeViewModel,
            expenseViewModel
        )
    }
}