package com.example.moneytracker.components.alert.lendDialog

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import com.example.moneytracker.components.alert.AmountTextField
import com.example.moneytracker.components.alert.DescriptionTextField
import com.example.moneytracker.components.alert.DialogDataClass
import com.example.moneytracker.components.date.ClickableDateText
import com.example.moneytracker.components.date.getCurrentDate
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.models.lend.LendViewModel
import java.util.Locale

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
            label = "Lend amount",
            placeholder = "0,0"
        )

        DescriptionTextField(
            state = lendDescState,
            placeholder = "lend to"
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
fun ShowLendAlertDialog(
    showDialog: MutableState<Boolean>,
    lendState: MutableState<TextFieldValue>,
    lendDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    lendViewModel: LendViewModel,
) {

    // Dialog data class
    val dialogDataClass = DialogDataClass()

    // App context
    val context = LocalContext.current

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
                    var bothAreFielded = lendState.value.text.isNotEmpty() &&
                            lendDescState.value.text.isNotEmpty()
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        enabled = bothAreFielded,
                        onClick = {
                            // Retrieve the entered text
                            val lend = lendState.value.text.replace(" ", "")
                            var dayOfWeek = dayOfWeekState.value.text
                            var day = dayState.value.text
                            var month = monthState.value.text
                            var year = yearState.value.text
                            val description = lendDescState.value.text

                            if (lend != "" && description != "") {
                                // Get the current date: currentDate
                                val currentDate: Map<String, String> = getCurrentDate()

                                if (dayOfWeek == "") {
                                    dayOfWeek = currentDate["dayOfWeek"].toString()
                                    month = currentDate["month"].toString()
                                    year = currentDate["year"].toString()
                                    day = currentDate["dayOfMonth"].toString()
                                }

                                // Example usage: Insert user
                                lendViewModel.insertLend(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    description=if (description.isDigitsOnly()) description
                                    else description.lowercase(Locale.ROOT),
                                    lend = lend.toDouble()
                                )

                                Toast.makeText(
                                    context,
                                    "You have given loan of $lend to $description",
                                    Toast.LENGTH_LONG).show()

                                lendDescState.value = TextFieldValue("")
                                dayOfWeekState.value = TextFieldValue("")
                                dayState.value = TextFieldValue("")
                                monthState.value = TextFieldValue("")
                                yearState.value = TextFieldValue("")
                                lendState.value = TextFieldValue("")
                            } else{
                                Toast.makeText(
                                    context,
                                    "You given ${
                                        if (lend.isEmpty()) "Lend Amount" else "Description"
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
