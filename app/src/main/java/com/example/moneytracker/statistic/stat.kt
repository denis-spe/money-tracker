package com.example.moneytracker.statistic

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun TotalSumViewer(
    income: Double?,
    expense: Double?
){
    if (income != null) {

        // Rounding the income
        val bigDecimal = BigDecimal(income)
        val scaledIncome = bigDecimal.setScale(2, RoundingMode.UP)

        Text(
            text = "Income: $scaledIncome",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp
        )
    }
    else{
        Text(
            text = "Income: 0.0",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp
        )
    }

    if (expense != null) {

        // Rounding the income
        val bigDecimal = BigDecimal(expense)
        val scaledExpense = bigDecimal.setScale(2, RoundingMode.UP)

        Text(
            text = "Expenses: $scaledExpense",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp
        )
    }
    else{
        Text(
            text = "Expenses: 0.0",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp
        )
    }
}