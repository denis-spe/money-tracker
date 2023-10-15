package com.example.moneytracker

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.pages.main.MainPage
import com.example.moneytracker.pages.MonthPage


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "RememberReturnType"
)
@Composable
fun App(
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomePage") {
        composable("HomePage") { MainPage (
            navController = navController,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel
        ) }

        composable(
            "YearPage/{year}",
            arguments = listOf(
                navArgument("year") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val year = backStackEntry.arguments?.getString("year")
            if (year != null) {
                MonthPage(
                    year,
                    navController = navController,
                    incomeViewModel = incomeViewModel,
                    expenseViewModel = expenseViewModel
                )
            }
        }
    }
}
