package com.example.moneytracker.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.components.charts.DonutChartInput
import com.example.moneytracker.components.charts.DonutPieChart
import com.example.moneytracker.components.date.getCurrentDate
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.lend.LendViewModel

class CurrentDebtAndLending(
    private val navController: NavController,
    private val debtViewModel: DebtViewModel,
    private val lendViewModel: LendViewModel
) {

    // Get the current date.
    private val calendar = getCurrentDate()

    // Get the current year.
    private val currentYear = calendar["year"]


    @Composable
    fun Debt(){
        // Get recent debt in a year
        debtViewModel.getAllDebtsForAYear(currentYear.toString())

        val debts: List<Double>? = debtViewModel
            .liveDebtsAYear.observeAsState()
            .value
        val recentlyDebt = debts?.getOrElse(debts.lastIndex) { 0.0 }

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

                // Get recent description in a year
                debtViewModel.getAllDescriptionForAYear(currentYear.toString())

                    val descriptions: List<String>? = debtViewModel
                        .liveDescAYear.observeAsState()
                        .value
                    val recentlyDesc = descriptions?.getOrElse(descriptions.lastIndex) { "" }

                    Text(
                        text = "Amount: $recentlyDebt",
                        fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                        fontSize = mainDataclass.lendAndDebtTextFontSize,
                        color = mainDataclass.debtsColor
                    )

                    Text(
                        text = "From: ${if (recentlyDesc != null && recentlyDesc.length > 4) 
                            recentlyDesc.ifEmpty { "DESC" }.substring(0, 4).uppercase() 
                        else recentlyDesc?.ifEmpty { "DESC" }?.uppercase() ?: "DESC"}  ",
                        fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                        fontSize = mainDataclass.lendAndDebtTextFontSize,
                        color = mainDataclass.debtsColor
                    )

            }
        }
    }

    @Composable
    fun Lend(){
        // Get recent lend in a year
        lendViewModel.getAllLendingForAYear(currentYear.toString())

        val lends: List<Double>? = lendViewModel.liveLendAYear
            .observeAsState()
            .value
        val recentlyLend = lends?.getOrElse(lends.lastIndex) { 0.0 }

        lendViewModel.getAllDescriptionForAYear(currentYear.toString())
        val descriptions: List<String>? = lendViewModel
            .liveDescAYear.observeAsState()
            .value
        val recentlyDesc = descriptions?.getOrElse(descriptions.lastIndex) { "" }

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
                    text = "Amount:  $recentlyLend",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.lendColor
                )
                Text(
                    text = "To: ${if (recentlyDesc != null && recentlyDesc.length > 4)
                        recentlyDesc.ifEmpty { "DESC" }.substring(0, 4).uppercase()
                    else recentlyDesc?.ifEmpty { "DESC" }?.uppercase() ?: "DESC"}  ",
                    fontWeight = mainDataclass.lendAndDebtTextFontWeight,
                    fontSize = mainDataclass.lendAndDebtTextFontSize,
                    color = mainDataclass.lendColor
                )
            }
        }
    }

    @Composable
    fun DonutChart(){
        // Get recent debt in a year
        debtViewModel.getAllDebtsForAYear(currentYear.toString())

        val debts: List<Double>? = debtViewModel
            .liveDebtsAYear.observeAsState()
            .value
        val recentlyDebt = debts?.getOrElse(debts.lastIndex) { 0.0 }

        // Get recent lend in a year
        lendViewModel.getAllLendingForAYear(currentYear.toString())

        val lends: List<Double>? = lendViewModel.liveLendAYear
            .observeAsState()
            .value
        val recentlyLend = lends?.getOrElse(lends.lastIndex) { 0.0 }


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
                val listOfDonutPieInput: List<DonutChartInput> = listOf(
                    DonutChartInput(
                        recentlyDebt ?: 0.0,
                        mainDataclass.debtsColor
                    ),
                    DonutChartInput(
                        recentlyLend ?: 0.0,
                        mainDataclass.lendColor
                    )
                )

                DonutPieChart(data = listOfDonutPieInput, modifier = Modifier.size(95.dp))
            }
        }
    }


    @Composable
    fun DebtAndLendingContainer(){

        Spacer(modifier = Modifier.height(5.dp) )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Current Debt And Lend",
                fontSize = mainDataclass.currentFontSize,
                fontWeight = mainDataclass.fontWeight
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

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
            Lend()
            Spacer(modifier = Modifier.width(1.dp))
            DonutChart()
            Spacer(modifier = Modifier.width(1.dp))
            Debt()
        }
    }
}