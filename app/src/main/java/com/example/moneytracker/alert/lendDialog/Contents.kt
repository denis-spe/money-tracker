package com.example.moneytracker.alert.lendDialog

import CurrencySymbolByLocation
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.alert.AmountTextField
import com.example.moneytracker.alert.DescriptionTextField
import com.example.moneytracker.alert.DialogDataClass
import com.example.moneytracker.alert.calenderContent
import com.example.moneytracker.alert.getDayOfWeek
import com.example.moneytracker.models.income.IncomeViewModel
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun lendAlertDialogContent(
    lendState: MutableState<TextFieldValue>,
    lendDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    mContext: Context = LocalContext.current
) {

    // Dialog data class
    val dialogDataClass = DialogDataClass()

    Column {

        AmountTextField(
            state = lendState,
            label = "Amount",
            placeholder = "0,0"
        )

        DescriptionTextField(
            state = lendDescState,
            placeholder = "lend to"
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowLendAlertDialog(
    showDialog: MutableState<Boolean>,
    lendState: MutableState<TextFieldValue>,
    lendDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    incomeViewModel: IncomeViewModel,
) {

    // Dialog data class
    val dialogDataClass = DialogDataClass()

    Column {
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        text = "Lend",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    lendAlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        lendDescState = lendDescState,
                        lendState = lendState
                    )
                },
                confirmButton = {
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        onClick = {
                            // Retrieve the entered text
                            val earned = lendState.value.text
                            val dayOfWeek = dayOfWeekState.value.text
                            val day = dayState.value.text
                            val month = monthState.value.text
                            val year = yearState.value.text

                            if (earned != "" && dayOfWeek != "") {
                                // Example usage: Insert user
                                incomeViewModel.insertUser(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    earned = earned.toDouble()
                                )
                                lendState.value = TextFieldValue("")
                                dayOfWeekState.value = TextFieldValue("")
                                dayState.value = TextFieldValue("")
                                monthState.value = TextFieldValue("")
                                yearState.value = TextFieldValue("")
                            }

                            // Perform the desired action with the entered text
                            showDialog.value = false
                        }
                    ) {
                        Text(text = "Add")
                    }
                },
                dismissButton = {
                    Button(
                        colors = dialogDataClass.canalButtonColor,
                        shape = dialogDataClass.buttonShape,
                        onClick = {
                            showDialog.value = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }
}
