package com.example.moneytracker.pages.scaffold

import androidx.compose.ui.graphics.Color
import com.example.moneytracker.R

data class ScaffoldDataClass(
    val incomeColor: Color = Color(76, 175, 80, 255),
    val expenseColor: Color = Color(223, 7, 56, 255),
    val remainderColor: Color = Color(3, 169, 244, 255),
    val debtsColor: Color = Color(255, 152, 0, 255),
    val debtsIcon: Map<String, Int> = mapOf(
        "closed" to R.drawable.lending,
        "opened" to R.drawable.lending_opened
    ),
    val incomeIcon: Map<String, Int> = mapOf(
        "closed" to R.drawable.income,
        "opened" to R.drawable.income_opened
    ),
    val expenseIcon: Map<String, Int> = mapOf(
        "closed" to R.drawable.expenses,
        "opened" to R.drawable.expenses_opened
    ),
)
