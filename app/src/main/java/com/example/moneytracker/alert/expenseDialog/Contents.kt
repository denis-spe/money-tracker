package com.example.moneytracker.alert.expenseDialog

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
import com.example.moneytracker.alert.AmountTextField
import com.example.moneytracker.alert.DescriptionTextField
import com.example.moneytracker.alert.DialogDataClass
import com.example.moneytracker.date.ClickableDateText
import com.example.moneytracker.date.getCurrentDate
import com.example.moneytracker.models.expense.ExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun expenseAlertDialogContent(
    expenseState: MutableState<TextFieldValue>,
    expenseDescState: MutableState<TextFieldValue>,
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
            state = expenseState,
            label = "Expense amount",
            placeholder = "0,0"
        )

        DescriptionTextField(
            state = expenseDescState,
            placeholder = "On what?"
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
fun ShowExpenseAlertDialog(
    showDialog: MutableState<Boolean>,
    expenseState: MutableState<TextFieldValue>,
    expenseDescState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    expenseViewModel: ExpenseViewModel,
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
                        text = "Expense",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    expenseAlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        expenseState = expenseState,
                        expenseDescState = expenseDescState
                    )
                },
                confirmButton = {
                    var bothAreFielded = expenseState.value.text.isNotEmpty() &&
                            expenseDescState.value.text.isNotEmpty()
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        enabled = bothAreFielded,
                        onClick = {
                            // Retrieve the entered text
                            val expense = expenseState.value.text.replace(" ", "")
                            var dayOfWeek = dayOfWeekState.value.text
                            var day = dayState.value.text
                            var month = monthState.value.text
                            var year = yearState.value.text
                            var description = expenseDescState.value.text

                            if (expense.isNotEmpty() && description.isNotEmpty()) {
                                // Get the current date: currentDate
                                val currentDate: Map<String, String> = getCurrentDate()

                                if (dayOfWeek.isEmpty()) {
                                    dayOfWeek = currentDate["dayOfWeek"].toString()
                                    month = currentDate["month"].toString()
                                    year = currentDate["year"].toString()
                                    day = currentDate["dayOfMonth"].toString()
                                }

                                // Example usage: Insert user
                                expenseViewModel.insertExpense(
                                    dayOfWeek = dayOfWeek,
                                    day = day,
                                    month = month,
                                    year = year,
                                    description = description,
                                    expense = expense.toDouble()
                                )
                                Toast.makeText(
                                    context,
                                    "You spent $expense on $description",
                                    Toast.LENGTH_LONG).show()

                                expenseState.value = TextFieldValue("")
                                dayOfWeekState.value = TextFieldValue("")
                                dayState.value = TextFieldValue("")
                                monthState.value = TextFieldValue("")
                                yearState.value = TextFieldValue("")
                                expenseDescState.value = TextFieldValue("")
                            } else{
                                Toast.makeText(
                                    context,
                                    "Insert The ${
                                        if (expense.isEmpty()) "Spent Amount" else "Spent Description"
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