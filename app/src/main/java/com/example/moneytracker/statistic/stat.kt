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
import limitDigit
import shortenNumber
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

@Composable
fun CurrentDateStats(
    income: Double?,
    expense: Double?
) {
    val statDataclass = StatDataclass()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {

            Text(
                text = "Income:",
                fontSize = statDataclass.fontSize,
                fontWeight = FontWeight.Bold,
                color = statDataclass.incomeColor
            )

            Text(
                text = "Expenses:",
                fontSize = statDataclass.fontSize,
                fontWeight = FontWeight.Bold,
                color = statDataclass.expenseColor
            )

            Text(
                text = "Remainder:",
                fontSize = statDataclass.fontSize,
                fontWeight = FontWeight.Bold,
                color = statDataclass.remainderColor
            )
        }

        Spacer(modifier = Modifier.width(9.dp))

        Column {

            /**
             * Income
             */

            if (income != null) {

                // Rounding the income
                val bigDecimal = BigDecimal(income)
                val scaledIncome = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = shortenNumber(scaledIncome).limitDigit(7),
                    color = statDataclass.incomeColor,
                    fontSize = statDataclass.fontSize,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "0,0",
                    color = statDataclass.incomeColor,
                    fontSize = statDataclass.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            /**
             * Expenses
             */
            if (expense != null) {

                // Rounding the income
                val bigDecimal = BigDecimal(expense)
                val scaledExpense = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = if (income == null) "-${
                        shortenNumber(scaledExpense)
                            .limitDigit(7)}"
                           else shortenNumber(scaledExpense)
                               .limitDigit(7),
                    color = statDataclass.expenseColor,
                    fontSize = statDataclass.fontSize,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "0,0",
                    color = statDataclass.expenseColor,
                    fontSize = statDataclass.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            /**
             * Remainder
             */
            if (income != null && expense != null) {
                val amount = (income - expense)
                // Rounding the amount
                val bigDecimal = BigDecimal(amount.absoluteValue)
                val scaledAmount = bigDecimal.setScale(2, RoundingMode.UP)

                if (amount > 0.0){
                    Text(
                        text = shortenNumber(scaledAmount)
                            .limitDigit(7),
                        color = statDataclass.incomeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = statDataclass.fontSize,
                    )
                } else if(amount == 0.0){
                    Text(
                        text = shortenNumber(scaledAmount)
                            .limitDigit(7),
                        color = statDataclass.remainderColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = statDataclass.fontSize,
                    )
                } else{
                    Text(
                        text = "-${shortenNumber(scaledAmount)
                            .limitDigit(7)}",
                        color = statDataclass.expenseColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = statDataclass.fontSize,
                    )
                }
            } else if (
                expense != null) {
                // Rounding the amount
                val bigDecimal = BigDecimal(expense)
                val scaledExpense = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = "-${shortenNumber(scaledExpense)
                        .limitDigit(7)}",
                    color = statDataclass.expenseColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = statDataclass.fontSize,
                )
            } else if (
                income != null) {
                // Rounding the amount
                val bigDecimal = BigDecimal(income)
                val scaledIncome = bigDecimal.setScale(2, RoundingMode.UP)

                Text(
                    text = shortenNumber(scaledIncome)
                        .limitDigit(7),
                    color = statDataclass.incomeColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = statDataclass.fontSize,
                )
            }else {
                Text(
                    text = "0,0",
                    color = statDataclass.remainderColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = statDataclass.fontSize,
                )
            }
        }
    }
}


