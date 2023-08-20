package com.example.moneytracker.alert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import com.example.moneytracker.models.income.IncomeViewModel

// Dialog data class
val dialogDataClass = DialogDataClass()

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowIncomeAlertDialog(
    showDialog: MutableState<Boolean>,
    earnedState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    incomeViewModel: IncomeViewModel,
) {

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
                    AlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        earnedState = earnedState
                    )
                },
                confirmButton = {
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        onClick = {
                            // Retrieve the entered text
                            val earned = earnedState.value.text
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
                                earnedState.value = TextFieldValue("")
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowExpenseAlertDialog(
    showDialog: MutableState<Boolean>,
    earnedState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    incomeViewModel: IncomeViewModel,
) {

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
                    AlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        earnedState = earnedState
                    )
                },
                confirmButton = {
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        onClick = {
                            // Retrieve the entered text
                            val earned = earnedState.value.text
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
                                earnedState.value = TextFieldValue("")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDebtAlertDialog(
    showDialog: MutableState<Boolean>,
    earnedState: MutableState<TextFieldValue>,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    incomeViewModel: IncomeViewModel,
) {

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
                    AlertDialogContent(
                        dayOfWeekState = dayOfWeekState,
                        dayState = dayState,
                        monthState = monthState,
                        yearState = yearState,
                        earnedState = earnedState
                    )
                },
                confirmButton = {
                    Button(
                        colors = dialogDataClass.addButtonColor,
                        shape = dialogDataClass.buttonShape,
                        onClick = {
                            // Retrieve the entered text
                            val earned = earnedState.value.text
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
                                earnedState.value = TextFieldValue("")
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