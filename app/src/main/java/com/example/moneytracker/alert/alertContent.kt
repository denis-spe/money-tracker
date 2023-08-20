package com.example.moneytracker.alert

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogContent(
    earnedState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    mContext: Context = LocalContext.current
) {

    // Dialog data class
    val dialogDataClass = DialogDataClass()

    Column {
        TextField(
            value = earnedState.value,
            onValueChange = { newValue ->
                earnedState.value = newValue
            },
            label = { Text(text = "Amount") },
            modifier = Modifier.padding(16.dp),
            placeholder = { Text(text = "0.0") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            colors = dialogDataClass.dateButtonColor,
            shape = dialogDataClass.buttonShape,
            onClick = {
                calenderContent(
                    mContext,
                    dayOfWeekState = dayOfWeekState,
                    dayState = dayState,
                    monthState = monthState,
                    yearState = yearState,
                ).show()
            }) {
            Row {
                Column {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date range",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column {

                    val day = dayState.value.text

                    if (day == "") {
                        // Get the current date.
                        val calendar = Calendar.getInstance()

                        // Get the current year.
                        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                        // Get the current year.
                        val currentMonth = calendar.get(Calendar.MONTH)

                        // Get the current year.
                        val currentYear = calendar.get(Calendar.YEAR)

                        val weekDay = getDayOfWeek(
                            "$currentDayOfMonth/${currentMonth + 1}/$currentYear"
                        )

                        Text(
                            text = "Select date",
                            fontSize = 15.sp
                        )
                        Text(
                            text = "default $weekDay $currentDayOfMonth" +
                                    "/$currentMonth" + "/$currentMonth",
                            fontSize = 15.sp
                        )
                    } else {
                        Text(
                            text = "Selected",
                            fontSize = 15.sp
                        )
                        Text(
                            text = "${dayOfWeekState.value.text} ${dayState.value.text}" +
                                    "/${monthState.value.text}" +
                                    "/${yearState.value.text}",
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

    }
}



