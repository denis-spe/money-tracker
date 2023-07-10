package com.example.moneytracker.pages

import YearExpander
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moneytracker.alert.getDayOfWeek
import com.example.moneytracker.alert.getMonthName
import com.example.moneytracker.charts.MyScreen
import com.example.moneytracker.models.EarnedViewModel
import com.example.moneytracker.pages.scaffold.ScaffoldComponent
import com.example.moneytracker.statistic.sum
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavHostController, earnedViewModel: EarnedViewModel) {
    val showDialog = remember { mutableStateOf(false) }


    // Get the unique years
    earnedViewModel.getUniqueYear()

    // load the live updated data
    val earnings: List<String> by earnedViewModel.liveStringData.observeAsState(emptyList())

    // Scaffold Component
    ScaffoldComponent(
        showDialog = showDialog,
        earnedViewModel = earnedViewModel
    ) {
        ScaffoldMainPageContents(
            navController,
            earnings = earnings,
            earnedViewModel = earnedViewModel
        )
    }
}

@Composable
fun ScaffoldMainPageContents(
    navController: NavController,
    earnings: List<String>,
    earnedViewModel: EarnedViewModel
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
                Container(
                    height = 0.14f,
                    firstPart = {
                        Text(
                            text = "Day",
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (currentDayOfMonth < 10)
                            Text(
                                text = "$weekDay, 0$currentDayOfMonth",
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 30.sp
                            )
                        else
                            Text(
                                text = "$weekDay, $currentDayOfMonth",
                                fontWeight = FontWeight(600),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 30.sp
                            )
                    },
                    secondPart = { /*TODO*/ }
                ) {
                    // fetch the live data
                    earnedViewModel.getTotalEarnedADay(
                        currentDayOfMonth.toString(),
                        getMonthName(currentMonth),
                        currentYear.toString()
                    )

                    // Get list of earning in a day
                    val currentEarningADay: Double? = earnedViewModel
                        .liveEarnedADay.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    sum(income = currentEarningADay, expense = emptyList())
                }
                Spacer(modifier = Modifier.height(5.dp))


                /*
                    Month Container
                 */
                Container(
                    height = 0.18f,
                    firstPart = {
                        Text(
                            text = "Month",
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = getMonthName(currentMonth),
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 30.sp
                        )
                    },
                    secondPart = { /*TODO*/ }
                ) {
                    // fetch the live data
                    earnedViewModel.getTotalEarnedAMonth(
                        getMonthName(currentMonth),
                        currentYear.toString()
                    )

                    // Get list of earning in a year
                    val currentEarningAMonth: Double? = earnedViewModel
                        .liveEarnedAMonth.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    sum(income = currentEarningAMonth, expense = emptyList())
                }
                Spacer(modifier = Modifier.height(5.dp))


                /*
                    Year Container
                 */
                Container(
                    height = 0.19f,
                    firstPart = {
                        Text(
                            text = "Year",
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = currentYear.toString(),
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 30.sp
                        )
                    },
                    secondPart = { /*TODO*/ }
                ) {
                    // fetch the live data
                    earnedViewModel.getTotalEarnedAYear(currentYear.toString())

                    // Get list of earning in a year
                    val currentEarningAYear: Double? = earnedViewModel
                        .liveEarnedAYear.observeAsState(0.0).value

                    Spacer(modifier = Modifier.height(5.dp))
                    sum(income = currentEarningAYear, expense = emptyList())
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
////                            YearExpander(idx, earnedViewModel)
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
fun Container(
    height: Float = 0.13f,
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
            .clip(RoundedCornerShape(
                topStart = 9.dp, topEnd = 9.dp,
                bottomStart = bottomStart.dp,
                bottomEnd = bottomEnd.dp
            ))
            .fillMaxWidth()
            .fillMaxHeight(height)
            .background(
                Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray)),
                alpha = 0.3f
            )
            .padding(4.dp)
    ) {

        // Current date
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.2f)
                .padding(4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            firstPart()
        }
        Spacer(modifier = Modifier.width(2.dp))

        // Pie chart
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            secondPart()
        }
        Spacer(modifier = Modifier.width(2.dp))

        // Statistic section
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .padding(1.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            thirdPart()
        }

        // Drop contents
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f),
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

            Column() {
                Text(text = "mean")
            }
        }
        expandedIcon = Icons.Default.KeyboardArrowDown
    }
}