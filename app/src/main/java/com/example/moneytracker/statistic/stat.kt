package com.example.moneytracker.statistic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.pages.scaffold.ScaffoldDataClass
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun CurrentDateStats(
    income: Double?,
    expense: Double?
) {
    val color = ScaffoldDataClass()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {

            Text(
                text = "Income:",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Expenses:",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Remainder:",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column {

            if (income != null) {

                // Rounding the income
                val bigDecimal = BigDecimal(income)
                val scaledIncome = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = shortenNumber(scaledIncome),
                    color = color.incomeColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "0,0",
                    color = color.incomeColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (expense != null) {

                // Rounding the income
                val bigDecimal = BigDecimal(expense)
                val scaledExpense = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = if (income == null) "-${shortenNumber(scaledExpense)}"
                           else shortenNumber(scaledExpense),
                    color = color.expenseColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "0,0",
                    color = color.expenseColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (income != null && expense != null) {
                val amount = (income - expense)
                // Rounding the amount
                val bigDecimal = BigDecimal(amount)
                val scaledAmount = bigDecimal.setScale(2, RoundingMode.UP)

                if (amount > 0.0){
                    Text(
                        text = shortenNumber(scaledAmount),
                        color = color.incomeColor,
                        fontWeight = FontWeight.Bold
                    )
                } else if(amount == 0.0){
                    Text(
                        text = shortenNumber(scaledAmount),
                        color = color.remainderColor,
                        fontWeight = FontWeight.Bold
                    )
                } else{
                    Text(
                        text = shortenNumber(scaledAmount),
                        color = color.expenseColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (
                expense != null) {
                // Rounding the amount
                val bigDecimal = BigDecimal(expense)
                val scaledExpense = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = "-${shortenNumber(scaledExpense)}",
                    color = color.expenseColor,
                    fontWeight = FontWeight.Bold
                )
            } else if (
                income != null) {
                // Rounding the amount
                val bigDecimal = BigDecimal(income)
                val scaledIncome = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = shortenNumber(scaledIncome),
                    color = color.incomeColor,
                    fontWeight = FontWeight.Bold
                )
            }else {
                Text(
                    text = "0,0",
                    color = color.remainderColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


fun shortenNumber(number: BigDecimal): String {
    val suffixes = arrayOf("", "K", "M", "B", "T") // Add more suffixes as needed
    var index = 0
    var num = number.toDouble()

    while (num >= 1000 && index < suffixes.size - 1) {
        num /= 1000
        index++
    }

    return "%.2f%s".format(num, suffixes[index])
}