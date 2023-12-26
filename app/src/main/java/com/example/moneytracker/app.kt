package com.example.moneytracker

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.models.lend.LendViewModel
import com.example.moneytracker.pages.pagingManager.PagingManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "RememberReturnType"
)
@Composable
fun App(
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    lendViewModel: LendViewModel,
    debtViewModel: DebtViewModel,
    auth: FirebaseAuth,
    currentUser: FirebaseUser?,
) {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "HomePage") {
//        composable("HomePage") { MainPage (
//            navController = navController,
//            incomeViewModel = incomeViewModel,
//            expenseViewModel = expenseViewModel,
//            lendViewModel = lendViewModel,
//            debtViewModel=debtViewModel
//        ) }
//
//        composable(
//            "YearPage/{year}",
//            arguments = listOf(
//                navArgument("year") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//            val year = backStackEntry.arguments?.getString("year")
//            if (year != null) {
//                MonthPage(
//                    year,
//                    navController = navController,
//                    incomeViewModel = incomeViewModel,
//                    expenseViewModel = expenseViewModel,
//                    lendViewModel = lendViewModel,
//                    debtViewModel=debtViewModel
//                )
//            }
//        }
//    }
    PagingManager(
        auth = auth,
        currentUser = currentUser
    )
}
