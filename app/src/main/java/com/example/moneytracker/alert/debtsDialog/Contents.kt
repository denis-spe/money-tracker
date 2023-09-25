package com.example.moneytracker.alert.debtsDialog

import android.content.Context
import android.os.Build
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
import com.example.moneytracker.alert.AmountTextField
import com.example.moneytracker.alert.DescriptionTextField
import com.example.moneytracker.alert.DialogDataClass
import com.example.moneytracker.date.ClickableDateText
import com.example.moneytracker.models.income.IncomeViewModel

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

    // Dialog data class
    val dialogDataClass = DialogDataClass()

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
                            val dayOfWeek = dayOfWeekState.value.text
                            val day = dayState.value.text
                            val month = monthState.value.text
                            val year = yearState.value.text
                            val description = debtDescState.value.text

                            if (debt != "" && dayOfWeek != "") {
                                // Example usage: Insert user
                                incomeViewModel.insertUser(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    earned = debt.toDouble(),
                                    description = description
                                )
                                debtState.value = TextFieldValue("")
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
