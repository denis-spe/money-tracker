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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moneytracker.models.EarnedViewModel
import com.example.moneytracker.pages.scaffold.ScaffoldComponent


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
    ){
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
){
    Column() {
        Header()

        LazyColumn(Modifier.padding(16.dp)) {
            items(earnings.size) { idx ->
                // Get the year
                val year = earnings[idx]

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
                                navController.navigate("YearPage/$year")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.width(100.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = year)
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
                                .height(150.dp)
                        ) {

                            YearExpander(idx, earnedViewModel)
                        }
                        expandedIcon = Icons.Default.KeyboardArrowDown
                    }
                }
            }
        }
    }

}