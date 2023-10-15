package com.example.moneytracker.statistic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class StatDataclass(
    val fontSize: TextUnit = 15.sp,
    val incomeColor: Color = Color(76, 175, 80, 255),
    val expenseColor: Color = Color(223, 7, 56, 255),
    val remainderColor: Color = Color(3, 169, 244, 255),
)
