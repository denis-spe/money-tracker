package com.example.moneytracker.alert.debtsDialog

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
import com.example.moneytracker.alert.AmountTextField
import com.example.moneytracker.alert.DescriptionTextField
import com.example.moneytracker.alert.DialogDataClass
import com.example.moneytracker.date.ClickableDateText
import com.example.moneytracker.date.getCurrentDate
import com.example.moneytracker.models.debts.DebtViewModel
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun debtsAlertDialogContent(
    debtState: MutableState<TextFieldValue>,
    debtDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    mContext: Context = LocalContext.current
) {

    Column {
        AmountTextField(
            state = debtState,
            label = "Pay your debt",
            placeholder = "0,0"
        )

        DescriptionTextField(
            state = debtDescState,
            placeholder = "Which debt?"
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
fun ShowDebtAlertDialog(
    showDialog: MutableState<Boolean>,
    debtState: MutableState<TextFieldValue>,
    debtDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    debtViewModel: DebtViewModel,
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
                        text = "Debts",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    debtsAlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        debtDescState = debtDescState,
                        debtState = debtState
                    )
                },
                confirmButton = {
                    var bothAreFielded = debtState.value.text.isNotEmpty() &&
                            debtDescState.value.text.isNotEmpty()

                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        enabled = bothAreFielded,
                        onClick = {
                            // Retrieve the entered text
                            val debt = debtState.value.text.replace(" ", "")
                            var dayOfWeek = dayOfWeekState.value.text
                            var day = dayState.value.text
                            var month = monthState.value.text
                            var year = yearState.value.text
                            val description = debtDescState.value.text

                            if (debt != "" && description != "") {
                                // Get the current date: currentDate
                                val currentDate: Map<String, String> = getCurrentDate()

                                if (dayOfWeek == "") {
                                    dayOfWeek = currentDate["dayOfWeek"].toString()
                                    month = currentDate["month"].toString()
                                    year = currentDate["year"].toString()
                                    day = currentDate["dayOfMonth"].toString()
                                }

                                // Example usage: Insert user
                                debtViewModel.insertDebt(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    description=if (description.isDigitsOnly()) description
                                    else description.lowercase(Locale.ROOT),
                                    debt = debt.toDouble()
                                )

                                Toast.makeText(
                                    context,
                                    "Earned Income From $description",
                                    Toast.LENGTH_LONG).show()

                                debtDescState.value = TextFieldValue("")
                                dayOfWeekState.value = TextFieldValue("")
                                dayState.value = TextFieldValue("")
                                monthState.value = TextFieldValue("")
                                yearState.value = TextFieldValue("")
                                debtState.value = TextFieldValue("")
                            } else{
                                Toast.makeText(
                                    context,
                                    "Insert The ${
                                        if (debt.isEmpty()) "Income Amount" else "Description"
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
