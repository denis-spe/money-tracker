package com.example.moneytracker.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moneytracker.components.charts.DonutChartInput
import com.example.moneytracker.components.charts.DonutPieChart
import com.example.moneytracker.components.date.getCurrentDate
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.models.lend.LendViewModel
import com.example.moneytracker.components.scaffold.ScaffoldDataClass
import com.example.moneytracker.statistic.CurrentDateStats

class CurrentIncomeAndExpense(
    private val navController: NavController,
    private val incomeViewModel: IncomeViewModel,
    private val expenseViewModel: ExpenseViewModel
) {

    // Scaffold data class
    private val scaffoldDataClass = ScaffoldDataClass()

    // Get the current date.
    private val calendar = getCurrentDate()

    // Get the current year.
    private val currentDayOfMonth = calendar["dayOfMonth"]

    // Get the current year.
    private val currentMonth = calendar["month"]

    // Get the current year.
    private val currentYear = calendar["year"]

    private val weekDay: String? = calendar["dayOfWeek"]

    @Composable
    fun CurrentIncomeAndExpenseContainer() {
        /*
           Day Container
        */
        CurrentDateContainer(
            expandedContents = {
            },
            firstPart = {

                Text(
                    text = "Day",
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 0.1.dp)
                )

                Text(
                    text = weekDay ?: "",
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 0.1.dp),

                    )



                Text(
                    text = (
                            if (currentDayOfMonth?.length!! > 1) "$currentDayOfMonth"
                            else "0$currentDayOfMonth"),
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
            },
            secondPart = {
                // fetch the live data
                incomeViewModel.getTotalEarnedADay(
                    currentDayOfMonth.toString(),
                    currentMonth ?: "",
                    currentYear.toString()
                )
                expenseViewModel.getTotalExpenseADay(
                    currentDayOfMonth.toString(),
                    currentMonth ?: "",
                    currentYear.toString()
                )

                // Get list of earning in a day
                val currentEarningADay: Double? = incomeViewModel
                    .liveEarnedADay.observeAsState(0.0).value
                // Get list of expense in a day
                val currentExpenseADay: Double? = expenseViewModel
                    .liveExpenseADay.observeAsState(0.0).value

                val listOfDonutPieInput: List<DonutChartInput> = listOf(
                    DonutChartInput(
                        currentEarningADay ?: 0.0,
                        scaffoldDataClass.incomeColor
                    ),
                    DonutChartInput(
                        currentExpenseADay ?: 0.0,
                        scaffoldDataClass.expenseColor
                    )
                )

                DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
            }
        ) {
            // fetch the live data
            if (currentMonth != null) {
                incomeViewModel.getTotalEarnedADay(
                    currentDayOfMonth.toString(),
                    currentMonth,
                    currentYear.toString()
                )

                expenseViewModel.getTotalExpenseADay(
                    currentDayOfMonth.toString(),
                    currentMonth,
                    currentYear.toString()
                )
            }

            // Get list of earning in a day
            val currentEarningADay: Double? = incomeViewModel
                .liveEarnedADay.observeAsState(0.0).value

            // Get list of expense in a day
            val currentExpenseADay: Double? = expenseViewModel
                .liveExpenseADay.observeAsState(0.0).value

            Spacer(modifier = Modifier.height(5.dp))
            CurrentDateStats(income = currentEarningADay, expense = currentExpenseADay)
        }
        Spacer(modifier = Modifier.height(5.dp))


        /*
            Month Container
         */
        CurrentDateContainer(
            expandedContents = {

            },
            firstPart = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Month",
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp
                    )
                    Text(
                        text = currentMonth ?: "",
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp
                    )
                }
            },
            secondPart = {

                // fetch the live data
                if (currentMonth != null) {
                    incomeViewModel.getTotalEarnedAMonth(
                        currentMonth,
                        currentYear.toString()
                    )

                    expenseViewModel.getTotalExpenseAMonth(
                        currentMonth,
                        currentYear.toString()
                    )
                }

                // Get list of earning in a Month
                val currentEarningAMonth: Double? = incomeViewModel
                    .liveEarnedAMonth.observeAsState(0.0).value

                // Get list of expense in a Month
                val currentExpenseAMonth: Double? = expenseViewModel
                    .liveExpenseAMonth.observeAsState(0.0).value

                val listOfDonutPieInput: List<DonutChartInput> = listOf(
                    DonutChartInput(
                        currentEarningAMonth ?: 0.0,
                        scaffoldDataClass.incomeColor
                    ),
                    DonutChartInput(
                        currentExpenseAMonth ?: 0.0,
                        scaffoldDataClass.expenseColor
                    )
                )

                DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
            }
        ) {
            // fetch the live data
            if (currentMonth != null) {
                incomeViewModel.getTotalEarnedAMonth(
                    currentMonth,
                    currentYear.toString()
                )
                expenseViewModel.getTotalExpenseAMonth(
                    currentMonth,
                    currentYear.toString()
                )
            }

            // Get list of earning in a Month
            val currentEarningAMonth: Double? = incomeViewModel
                .liveEarnedAMonth.observeAsState(0.0).value

            // Get list of expense in a Month
            val currentExpenseAMonth: Double? = expenseViewModel
                .liveExpenseAMonth.observeAsState(0.0).value

            Spacer(modifier = Modifier.height(5.dp))
            CurrentDateStats(income = currentEarningAMonth, expense = currentExpenseAMonth)
        }
        Spacer(modifier = Modifier.height(5.dp))


        /*
            Year Container
         */
        CurrentDateContainer(
            expandedContents = {
                Row() {
                    Column() {
                        // fetch the live min earning for a year
                        incomeViewModel.getMinForEarningAYear(currentYear.toString())

                        // Get min in a year
                        val minEarningInYear: Double? = incomeViewModel
                            .liveMinForEarningAYear
                            .observeAsState(0.0).value
                        if (minEarningInYear != null) {
                            Text(
                                text = "Min Income: $minEarningInYear",
                                fontSize = 12.sp
                            )
                        }
                        if (minEarningInYear != null) {
                            Text(
                                text = "Min Expense: $minEarningInYear",
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        // fetch the live max earning for a year
                        incomeViewModel.getMaxForEarningAYear(currentYear.toString())

                        // Get max earning in a year
                        val maxEarningInYear: Double? = incomeViewModel
                            .liveMeanForEarningAYear.observeAsState(0.0).value
                        if (maxEarningInYear != null) {
                            Text(
                                text = "Max Income: $maxEarningInYear",
                                fontSize = 12.sp
                            )
                        }
                        if (maxEarningInYear != null) {
                            Text(
                                text = "Max Expense: $maxEarningInYear",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            firstPart = {
                Text(
                    text = "Year",
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 22.sp
                )
                Text(
                    text = currentYear.toString(),
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
            },
            secondPart = {

                // fetch the live data
                incomeViewModel.getTotalEarnedAYear(currentYear.toString())
                expenseViewModel.getTotalExpenseAYear(currentYear.toString())

                // Get list of earning in a year
                val currentEarningAYear: Double? = incomeViewModel
                    .liveEarnedAYear.observeAsState(0.0).value

                // Get list of expense in a year
                val currentExpenseAYear: Double? = expenseViewModel
                    .liveExpenseAYear.observeAsState(0.0).value

                val listOfDonutPieInput: List<DonutChartInput> = listOf(
                    DonutChartInput(
                        currentEarningAYear ?: 0.0,
                        scaffoldDataClass.incomeColor
                    ),
                    DonutChartInput(
                        currentExpenseAYear ?: 0.0,
                        scaffoldDataClass.expenseColor
                    )
                )

                DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
            }
        ) {
            // fetch the live data
            incomeViewModel.getTotalEarnedAYear(currentYear.toString())
            expenseViewModel.getTotalExpenseAYear(currentYear.toString())


            // Get list of earning in a year
            val currentEarningAYear: Double? = incomeViewModel
                .liveEarnedAYear.observeAsState(0.0).value
            // Get list of expense in a year
            val currentExpenseAYear: Double? = expenseViewModel
                .liveExpenseAYear.observeAsState(0.0).value

            Spacer(modifier = Modifier.height(5.dp))
            CurrentDateStats(income = currentEarningAYear, expense = currentExpenseAYear)
        }
    }

    @Composable
    private fun CurrentDateContainer(
        expandedContents: @Composable () -> Unit,
        firstPart: @Composable () -> Unit,
        secondPart: @Composable () -> Unit,
        thirdPart: @Composable () -> Unit,
    ) {
        var expanded by remember { mutableStateOf(false) }
        var expandedIcon by remember {
            mutableStateOf(Icons.Default.KeyboardArrowUp)
        }

        var bottomStart = 9
        var bottomEnd = 9

        if (expanded) {
            bottomStart = 0
            bottomEnd = 0
        }

        // Month Column
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 9.dp, topEnd = 9.dp,
                        bottomStart = bottomStart.dp,
                        bottomEnd = bottomEnd.dp
                    )
                )
                .fillMaxWidth()
                .height(mainDataclass.currentDateContainerHeight)
                .background(
                    Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray)),
                    alpha = 0.3f
                )
                .padding(1.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Current date
            Column(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                firstPart()
            }
            Spacer(modifier = Modifier.width(1.2.dp))

            // Pie chart
            Column(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.27f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                secondPart()
            }
            Spacer(modifier = Modifier.width(0.2.dp))

            // Statistic section
            Column(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .height(80.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                thirdPart()
            }

            Spacer(modifier = Modifier.width(1.dp))

            // Drop contents
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f),
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
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 9.dp,
                            bottomEnd = 9.dp
                        )
                    )
                    .background(
                        Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray)),
                        alpha = 0.3f
                    )
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    expandedContents()
                }
            }
            expandedIcon = Icons.Default.KeyboardArrowDown
        }
    }
}