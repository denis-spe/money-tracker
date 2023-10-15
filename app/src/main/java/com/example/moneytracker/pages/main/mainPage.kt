package com.example.moneytracker.pages.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moneytracker.charts.DonutChartInput
import com.example.moneytracker.charts.DonutPieChart
import com.example.moneytracker.date.getCurrentDate
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.pages.Header
import com.example.moneytracker.pages.scaffold.ScaffoldComponent
import com.example.moneytracker.pages.scaffold.ScaffoldDataClass
import com.example.moneytracker.statistic.CurrentDateStats

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
    expenseViewModel: ExpenseViewModel
) {

    // Scaffold Component
    ScaffoldComponent(
        incomeViewModel = incomeViewModel,
        expenseViewModel = expenseViewModel
    ) {
        MainPageContents(
            navController,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel
        )
    }
}

@Composable
fun MainPageContents(
    navController: NavController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel
) {

    val scaffoldDataClass = ScaffoldDataClass()

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


            // Get the current date.
            val calendar = getCurrentDate()

            // Get the current year.
            val currentDayOfMonth = calendar["dayOfMonth"]

            // Get the current year.
            val currentMonth = calendar["month"]

            // Get the current year.
            val currentYear = calendar["year"]

            val weekDay: String? = calendar["dayOfWeek"]



            item(
                1
//                modifier = Modifier.fillMaxSize()
            ) {

                /*
                    Day Container
                 */
                CurrentDateContainer(
//                    height = 0.25f,
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
//                    height = 0.95f,
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
//                    height = 0.25f,
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

            item(2) {
                debtAndLendingContainer()
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

@Composable
fun CurrentDateContainer(
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

@Composable
fun debtAndLendingContainer(){
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(9.dp))
            .height(mainDataclass.debtAndLendingContainerRowHeight)
            .background(
                Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray)),
                alpha = 0.1f
            )
            .padding(start = 9.dp, end = 9.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        /**
         * Lending
         */
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .fillMaxHeight()
                .width(mainDataclass.debtAndLendingSmallContainerWidth),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Lend",
                    fontSize = mainDataclass.lendAndDebtFontSize,
                    fontWeight = mainDataclass.fontWeight,
                    color = mainDataclass.lendColor
                )
            }
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Amount:  2121.76",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.lendColor
                )
                Text(
                    text = "To: Sam",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.lendColor
                )
            }
        }

        Spacer(modifier = Modifier.width(1.dp))

        /**
         * Doughnut chart
         */
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .fillMaxHeight()
                .width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Recently",
                    fontSize = mainDataclass.recentlyFontSize,
                    fontWeight = mainDataclass.fontWeight
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val listOfDonutPieInput: List<DonutChartInput> = listOf(
                    DonutChartInput(
                        2121.76,
                        mainDataclass.debtsColor
                    ),
                    DonutChartInput(
                        2121.76,
                        mainDataclass.lendColor
                    )
                )

                DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(95.dp))
            }



        }

        Spacer(modifier = Modifier.width(1.dp))

        /**
         * Debts
         */
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .fillMaxHeight()
                .width(mainDataclass.debtAndLendingSmallContainerWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Debts",
                    fontSize = mainDataclass.lendAndDebtFontSize,
                    fontWeight = mainDataclass.fontWeight,
                    color = mainDataclass.debtsColor
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "2121.76 :Amount",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.debtsColor
                )
                Text(
                    text = "From: Sam",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.debtsColor
                )
            }
        }
    }
}