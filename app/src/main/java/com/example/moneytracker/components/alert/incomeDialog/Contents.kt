package com.example.moneytracker.components.alert.incomeDialog

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.core.text.isDigitsOnly
import com.example.moneytracker.components.alert.AmountTextField
import com.example.moneytracker.components.date.ClickableDateText
import com.example.moneytracker.components.alert.DescriptionTextField
import com.example.moneytracker.components.alert.DialogDataClass
import com.example.moneytracker.components.date.getCurrentDate
import com.example.moneytracker.components.date.getMonthName
import com.example.moneytracker.models.income.IncomeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun incomeAlertDialogContent(
    incomeState: MutableState<TextFieldValue>,
    incomeDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    mContext: Context = LocalContext.current
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        AmountTextField(
            state = incomeState,
            label = "Income amount",
            placeholder = "0,0"
        )
        DescriptionTextField(
            state = incomeDescState,
            placeholder = "From Where?"
        )

        ClickableDateText(
            dayOfWeekState = dayOfWeekState,
            dayState = dayState,
            monthState = monthState,
            yearState = yearState
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowIncomeAlertDialog(
    showDialog: MutableState<Boolean>,
    incomeState: MutableState<TextFieldValue>,
    incomeDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    incomeViewModel: IncomeViewModel,
) {
    // App context
    val context = LocalContext.current

    // Dialog data class
    val dialogDataClass = DialogDataClass()

    Column {
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        text = "Income earnings",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    incomeAlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        incomeDescState = incomeDescState,
                        incomeState = incomeState
                    )
                },
                confirmButton = {
                    var bothAreFielded = incomeState.value.text.isNotEmpty() &&
                            incomeDescState.value.text.isNotEmpty()
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        enabled = bothAreFielded,
                        onClick = {
                            // Retrieve the entered text
                            val earned = incomeState.value.text.replace(" ", "")
                            var dayOfWeek = dayOfWeekState.value.text
                            var day = dayState.value.text
                            var month = monthState.value.text
                            var year = yearState.value.text
                            var description = incomeDescState.value.text

                            if (earned != "" && description != "") {
                                // Get the current date: currentDate
                                val currentDate: Map<String, String> = getCurrentDate()

                                if (dayOfWeek == "") {
                                    dayOfWeek = currentDate["dayOfWeek"].toString()
                                    month = currentDate["month"].toString()
                                    year = currentDate["year"].toString()
                                    day = currentDate["dayOfMonth"].toString()
                                }

                                // Example usage: Insert user
                                incomeViewModel.insertUser(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    description=if (description.isDigitsOnly()) description
                                                else description.lowercase(Locale.ROOT),
                                    earned = earned.toDouble()
                                )

                                Toast.makeText(
                                    context,
                                    "Earned Income From $description",
                                    Toast.LENGTH_LONG).show()

                                incomeState.value = TextFieldValue("")
                                dayOfWeekState.value = TextFieldValue("")
                                dayState.value = TextFieldValue("")
                                monthState.value = TextFieldValue("")
                                yearState.value = TextFieldValue("")
                                incomeDescState.value = TextFieldValue("")
                            } else{
                                Toast.makeText(
                                    context,
                                    "Insert The ${
                                        if (earned.isEmpty()) "Income Amount" else "Description"
                                    }",
                                    Toast.LENGTH_LONG).show()
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