package com.example.moneytracker.pages

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavHostController
import com.example.moneytracker.alert.getDayOfWeek
import com.example.moneytracker.alert.getMonthName
import com.example.moneytracker.charts.DonutChartInput
import com.example.moneytracker.charts.DonutPieChart
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.pages.scaffold.ScaffoldComponent
import com.example.moneytracker.statistic.TotalSumViewer
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavHostController, incomeViewModel: IncomeViewModel) {
    val showDialog = remember { mutableStateOf(false) }


    // Get the unique years
    incomeViewModel.getUniqueYear()

    // load the live updated data
    val earnings: List<String> by incomeViewModel.liveStringData.observeAsState(emptyList())

    // Scaffold Component
    ScaffoldComponent(
        showDialog = showDialog,
        incomeViewModel = incomeViewModel
    ) {
        ScaffoldMainPageContents(
            navController,
            earnings = earnings,
            incomeViewModel = incomeViewModel
        )
    }
}

@Composable
fun ScaffoldMainPageContents(
    navController: NavController,
    earnings: List<String>,
    incomeViewModel: IncomeViewModel
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {

        // Header contents
        Header()

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(21.dp))
                .background(
                    Color(0.4f, 0.4f, 0.4f, 0.3f)
                )
                .fillMaxSize()
                .padding(10.dp)
        ) {


            // Get the current date.
            val calendar = Calendar.getInstance()

            // Get the current year.
            val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Get the current year.
            val currentMonth = calendar.get(Calendar.MONTH)

            // Get the current year.
            val currentYear = calendar.get(Calendar.YEAR)

            val weekDay = getDayOfWeek("$currentDayOfMonth/${currentMonth + 1}/$currentYear")

            Column(
                modifier = Modifier.fillMaxSize()
            ){

                /*
                    Day Container
                 */
                CurrentDateContainer(
                    height = 0.14f,
                    expandedContents = {
                    },
                    firstPart = {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Day",
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 0.1.dp)
                            )

                            Text(
                                text = "$weekDay",
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 17.sp,
                                modifier = Modifier.padding(top = 0.1.dp),

                            )


                            if (currentDayOfMonth < 10) {
                                Text(
                                    text = "0$currentDayOfMonth",
                                    fontWeight = FontWeight(600),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 17.sp
                                )
                            }
                            else
                                Text(
                                    text = "$currentDayOfMonth",
                                    fontWeight = FontWeight(600),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 17.sp
                                )
                        }
                    },
                    secondPart = {
                        // fetch the live data
                        incomeViewModel.getTotalEarnedADay(
                            currentDayOfMonth.toString(),
                            getMonthName(currentMonth),
                            currentYear.toString()
                        )

                        // Get list of earning in a day
                        val currentEarningADay: Double? = incomeViewModel
                            .liveEarnedADay.observeAsState(0.0).value

                        val listOfDonutPieInput: List<DonutChartInput> = listOf(
                            DonutChartInput(currentEarningADay?:0.0, Color(76, 175, 80, 255)),
                            DonutChartInput(50.0, Color(223, 7, 56, 255))
                        )

                        DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
                    }
                ) {
                    // fetch the live data
                    incomeViewModel.getTotalEarnedADay(
                        currentDayOfMonth.toString(),
                        getMonthName(currentMonth),
                        currentYear.toString()
                    )

                    // Get list of earning in a day
                    val currentEarningADay: Double? = incomeViewModel
                        .liveEarnedADay.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    TotalSumViewer(income = currentEarningADay, expense = 0.0)
                }
                Spacer(modifier = Modifier.height(5.dp))


                /*
                    Month Container
                 */
                CurrentDateContainer(
                    height = 0.18f,
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
                                fontSize = 20.sp
                            )
                            Text(
                                text = getMonthName(currentMonth),
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 17.sp
                            )
                        }
                    },
                    secondPart = {

                        // fetch the live data
                        incomeViewModel.getTotalEarnedAMonth(
                            getMonthName(currentMonth),
                            currentYear.toString()
                        )

                        // Get list of earning in a Month
                        val currentEarningAMonth: Double? = incomeViewModel
                            .liveEarnedAMonth.observeAsState(0.0).value

                        val listOfDonutPieInput: List<DonutChartInput> = listOf(
                            DonutChartInput(currentEarningAMonth?:0.0, Color(76, 175, 80, 255)),
                            DonutChartInput(50.0, Color(223, 7, 56, 255))
                        )

                        DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
                    }
                ) {
                    // fetch the live data
                    incomeViewModel.getTotalEarnedAMonth(
                        getMonthName(currentMonth),
                        currentYear.toString()
                    )

                    // Get list of earning in a Month
                    val currentEarningAMonth: Double? = incomeViewModel
                        .liveEarnedAMonth.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    TotalSumViewer(income = currentEarningAMonth, expense = 0.0)
                }
                Spacer(modifier = Modifier.height(5.dp))


                /*
                    Year Container
                 */
                CurrentDateContainer(
                    height = 0.25f,
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
                            fontSize = 20.sp
                        )
                        Text(
                            text = currentYear.toString(),
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 17.sp
                        )
                    },
                    secondPart = {

                        // fetch the live data
                        incomeViewModel.getTotalEarnedAYear(currentYear.toString())

                        // Get list of earning in a year
                        val currentEarningAYear: Double? = incomeViewModel
                            .liveEarnedAYear.observeAsState(0.0).value

                        val listOfDonutPieInput: List<DonutChartInput> = listOf(
                            DonutChartInput(currentEarningAYear?:0.0, Color(76, 175, 80, 255)),
                            DonutChartInput(50.0, Color(223, 7, 56, 255))
                        )

                        DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(100.dp))
                    }
                ) {
                    // fetch the live data
                    incomeViewModel.getTotalEarnedAYear(currentYear.toString())

                    // Get list of earning in a year
                    val currentEarningAYear: Double? = incomeViewModel
                        .liveEarnedAYear.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    TotalSumViewer(income = currentEarningAYear, expense = 0.0)
                }

            }
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
    height: Float = 0.13f,
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

    if (expanded){
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
            .fillMaxHeight(height)
            .background(
                Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray)),
                alpha = 0.3f
            )
            .padding(1.dp)
    ) {

        // Current date
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            firstPart()
        }
        Spacer(modifier = Modifier.width(1.dp))

        // Pie chart
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            secondPart()
        }
        Spacer(modifier = Modifier.width(1.dp))

        // Statistic section
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxHeight()
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