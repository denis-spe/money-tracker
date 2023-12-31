package com.example.moneytracker.components.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.lend.LendViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.components.Header
import com.example.moneytracker.components.scaffold.ScaffoldComponent

/**
 * Dataclass
 */
val mainDataclass: MainDataclass = MainDataclass()

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(
    navController: NavHostController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    debtViewModel: DebtViewModel,
    lendViewModel: LendViewModel
) {

    // Scaffold Component
    ScaffoldComponent(
        incomeViewModel = incomeViewModel,
        expenseViewModel = expenseViewModel,
        debtViewModel = debtViewModel,
        lendViewModel = lendViewModel
    ) {
        MainPageContents(
            navController,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel,
            debtViewModel = debtViewModel,
            lendViewModel = lendViewModel
        )
    }
}

@Composable
fun MainPageContents(
    navController: NavController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    debtViewModel: DebtViewModel,
    lendViewModel: LendViewModel
) {

    val currentIncomeAndExpense = CurrentIncomeAndExpense(
        navController,
        incomeViewModel=incomeViewModel,
        expenseViewModel=expenseViewModel
    )

    val currentDebtAndLending = CurrentDebtAndLending(
        navController,
        debtViewModel=debtViewModel,
        lendViewModel=lendViewModel
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {

        // Header contents
        Header()

        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(21.dp))
                .background(
                    mainDataclass.lazyColumnBackgroundColor
                )
                .fillMaxSize()
                .padding(10.dp)
        ) {

            item(
                1
            ) {

                currentIncomeAndExpense.CurrentIncomeAndExpenseContainer()
            }

            item(2) {
                currentDebtAndLending.DebtAndLendingContainer()
            }

//            item(4) {
//                Column(
//                    modifier = Modifier
//                        .height(80.dp)
//                        .fillMaxWidth()
//                ) {
//                    Text(text = "Debts And Lending")
//                }
//            }
        }

//        LazyColumn(
//
//        ) {
//            items(earnings.size) { idx ->
//                // Get the year
//                val year = earnings[idx]
//
//                var expanded by remember { mutableStateOf(false) }
//                var expandedIcon by remember {
//                    mutableStateOf(Icons.Default.KeyboardArrowUp)
//                }
//                Column(
//                    Modifier
//                        .clip(RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .background(
//                                Color(red = 100, green = 100, blue = 100, alpha = 50)
//                            )
//                            .fillMaxWidth()
//                            .clickable {
//                                navController.navigate("YearPage/$year")
//                            },
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Column(
//                            modifier = Modifier.width(100.dp),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(text = year)
//                        }
//
//                        Spacer(modifier = Modifier.width(160.dp))
//
//
//                        Column(
//                            modifier = Modifier.width(100.dp),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            IconButton(onClick = {
//                                expanded = !expanded
//                                expandedIcon = Icons.Default.KeyboardArrowUp
//                            }) {
//                                Icon(
//                                    imageVector = expandedIcon,
//                                    contentDescription = "Expend Icon"
//                                )
//                            }
//                        }
//                    }
//
//                    if (expanded) {
//                        Row(
//                            modifier = Modifier
//                                .background(
//                                    Color(red = 100, green = 100, blue = 100, alpha = 50)
//                                )
//                                .fillMaxWidth()
//                                .height(150.dp)
//                        ) {
//
////                            YearExpander(idx, incomeViewModel)
//                            Column() {
//                                MyScreen()
//                            }
//                        }
//                        expandedIcon = Icons.Default.KeyboardArrowDown
//                    }
//                }
//            }
//        }
    }

}





