package com.example.moneytracker.components.alert

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class DialogDataClass(
    val buttonShape: RoundedCornerShape = RoundedCornerShape(3.dp),
    val addButtonColor: ButtonColors = ButtonColors(
        containerColor = Color(76, 175, 80, 255),
        contentColor = Color.White,
        disabledContainerColor = Color(76, 175, 80, 100),
        disabledContentColor = Color.White
    ),
    val canalButtonColor: ButtonColors = ButtonColors(
        containerColor = Color(223, 7, 56, 255),
        contentColor = Color.White,
        disabledContainerColor = Color.Red,
        disabledContentColor = Color.Magenta
    ),
    val dateButtonColor: ButtonColors = ButtonColors(
        containerColor = Color(8, 102, 93, 255),
        contentColor = Color.White,
        disabledContainerColor = Color.Red,
        disabledContentColor = Color.Magenta
    ),
)
