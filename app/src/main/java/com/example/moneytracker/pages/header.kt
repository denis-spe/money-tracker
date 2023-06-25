package com.example.moneytracker.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    year: String? = null,
    month: String? = null,
    onClick: (() -> Unit)? = null
){
    if (onClick != null) {
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Home")
        }
    }

    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Money Tracker",
            fontSize = 20.sp,
            fontWeight = FontWeight(900)
        )

        when{
            year != null -> {
                Text(
                    text = "Year $year",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(900)
                )
            }

            month != null -> {
                Text(
                    text = "Month $month",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(900)
                )
            }
        }

    }
}