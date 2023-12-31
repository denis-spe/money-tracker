package com.example.moneytracker.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.lend.LendViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.components.scaffold.ScaffoldComponent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthPage(
    year: String, navController: NavHostController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    lendViewModel: LendViewModel,
    debtViewModel: DebtViewModel
) {

    // Get the live added month
    incomeViewModel.getMonth(year)
    val earnings: List<String> by incomeViewModel.liveStringData.observeAsState(emptyList())

    // Scaffold Component
    ScaffoldComponent(
        incomeViewModel = incomeViewModel,
        expenseViewModel = expenseViewModel,
        lendViewModel=lendViewModel,
        debtViewModel = debtViewModel
    ) {
        ScaffoldMonthPageContents(
            year,
            navController = navController,
            earnings = earnings,
        )
    }
}

@Composable
fun ScaffoldMonthPageContents(
    year: String,
    navController: NavHostController,
    earnings: List<String>
){

    Column() {
        Header(
            year = year
        ){
            navController.navigateUp()
        }

        LazyColumn(Modifier.padding(16.dp)) {
            itemsIndexed(earnings) { _, item ->
                var expanded by remember { mutableStateOf(false) }
                var expandedIcon by remember {
                    mutableStateOf(Icons.Default.KeyboardArrowUp)
                }

                Column(
                    Modifier
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                Color(red = 100, green = 100, blue = 100, alpha = 50)
                            )
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("HomePage")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.width(100.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = item)
                        }

                        Spacer(modifier = Modifier.width(160.dp))


                        Column(
                            modifier = Modifier.width(100.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                expanded = !expanded
                                expandedIcon = Icons.Default.KeyboardArrowUp
                            }) {
                                Icon(
                                    imageVector = expandedIcon,
                                    contentDescription = "Expend Icon"
                                )
                            }
                        }
                    }

                    if (expanded) {
                        Row(
                            modifier = Modifier
                                .background(
                                    Color(red = 100, green = 100, blue = 100, alpha = 50)
                                )
                                .fillMaxWidth()
                        ) {
                            Text(text = year)
                        }
                        expandedIcon = Icons.Default.KeyboardArrowDown
                    }
                }
            }
        }
    }
}

