package com.example.moneytracker.pages.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MainDataclass(
    val lazyColumnBackgroundColor: Color = Color(
        0.4f,
        0.4f,
        0.4f,
        0.3f),
    val currentDateContainerHeight: Dp = 118.dp,
    val debtAndLendingContainerRowHeight: Dp = 150.dp,
    val debtAndLendingSmallContainerWidth: Dp = 120.dp,
    val lendColor: Color = Color(244, 67, 54, 255),
    val debtsColor: Color = Color(255, 152, 0, 255),
    val recentlyFontSize: TextUnit = 19.sp,
    val lendAndDebtFontSize: TextUnit = 17.sp,
    val fontWeight: FontWeight = FontWeight.ExtraBold,
    val lendAndDebtTextFontWeight: FontWeight = FontWeight.ExtraBold,
    val lendAndDebtTextFontSize: TextUnit = 13.sp
)
