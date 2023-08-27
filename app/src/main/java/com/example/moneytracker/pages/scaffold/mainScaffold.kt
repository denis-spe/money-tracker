package com.example.moneytracker.pages.scaffold

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.moneytracker.alert.incomeDialog.ShowIncomeAlertDialog
import com.example.moneytracker.alert.debtsDialog.ShowDebtAlertDialog
import com.example.moneytracker.alert.expenseDialog.ShowExpenseAlertDialog
import com.example.moneytracker.alert.lendDialog.ShowLendAlertDialog
import com.example.moneytracker.modelDrawer.ModelDrawerContents
import com.example.moneytracker.models.income.IncomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldComponent(
    incomeViewModel: IncomeViewModel,
    scaffoldContents: @Composable () -> Unit
) {

    val showIncomeDialog = remember { mutableStateOf(false) }
    val showDebtDialog = remember { mutableStateOf(false) }
    val showLendDialog = remember { mutableStateOf(false) }
    val showExpenseDialog = remember { mutableStateOf(false) }

    var isExpanded by remember { mutableStateOf(false) }
    val isBottomSheetOpen = remember { mutableStateOf(false) }

    val incomeState = remember { mutableStateOf(TextFieldValue()) }
    val expenseState = remember { mutableStateOf(TextFieldValue()) }
    val lendState = remember { mutableStateOf(TextFieldValue()) }
    val debtState = remember { mutableStateOf(TextFieldValue()) }

    val incomeDescState = remember { mutableStateOf(TextFieldValue()) }
    val lendDescState = remember { mutableStateOf(TextFieldValue()) }
    val debtDescState = remember { mutableStateOf(TextFieldValue()) }
    val expenseDescState = remember { mutableStateOf(TextFieldValue()) }


    val dayOfWeekState = remember { mutableStateOf(TextFieldValue()) }
    val dayState = remember { mutableStateOf(TextFieldValue()) }
    val monthState = remember { mutableStateOf(TextFieldValue()) }
    val yearState = remember { mutableStateOf(TextFieldValue()) }

    // stats data class
    val scaffoldDataClass = ScaffoldDataClass()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {

            val width = if (isExpanded) {
                130.dp
            } else {
                60.dp
            }

            Column(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.CenterEnd)
                    .width(width)
            ) {

                if (isExpanded) {

                    /**
                     * Income
                     */
                    FloatActionButton(
                        containerColor = scaffoldDataClass.incomeColor,
                        text = "Income",
                        icon = {
                            (if (showIncomeDialog.value) {
                                scaffoldDataClass.incomeIcon["opened"]
                            } else {
                                scaffoldDataClass.incomeIcon["closed"]
                            })?.let { painterResource(id = it) }?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = "Add Dialog",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    ) {
                        // OnClick listener
                        showIncomeDialog.value = true

                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    /**
                     * Expense
                     */
                    FloatActionButton(
                        containerColor = scaffoldDataClass.expenseColor,
                        text = "Expense",
                        icon = {
                            (if (showExpenseDialog.value) {
                                scaffoldDataClass.expenseIcon["opened"]
                            } else {
                                scaffoldDataClass.expenseIcon["closed"]
                            })?.let { painterResource(id = it) }?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = "Add Dialog",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    ) {

                        // OnClick listener
                        showExpenseDialog.value = true
                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    /**
                     * Lend
                     */
                    FloatActionButton(
                        containerColor = scaffoldDataClass.lendColor,
                        text = "Lend",
                        icon = {
                            (if (showLendDialog.value) {
                                scaffoldDataClass.lendIcon["opened"]
                            } else {
                                scaffoldDataClass.lendIcon["closed"]
                            })?.let { painterResource(id = it) }?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = "Add Dialog",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    ) {
                        showLendDialog.value = true
                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    /**
                     * Debts
                     */
                    FloatActionButton(
                        containerColor = scaffoldDataClass.debtsColor,
                        text = "Debts",
                        icon = {
                            (if (showDebtDialog.value) {
                                scaffoldDataClass.debtsIcon["opened"]
                            } else {
                                scaffoldDataClass.debtsIcon["closed"]
                            })?.let { painterResource(id = it) }?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = "Add Dialog",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    ) {
                        showDebtDialog.value = true
                    }

                    Spacer(modifier = Modifier.height(7.dp))
                }

                val expendBtnColor = if (isExpanded) {
                    Color(22, 123, 202, 255)
                } else {
                    Color(22, 123, 202, 128)
                }
                FloatActionButton(
                    containerColor = expendBtnColor,
                    icon = {
                        if (isExpanded)
                            Icon(
                                Icons.Outlined.Clear,
                                contentDescription = "Add Dialog",
                            )
                        else {
                            Icon(
                                Icons.Outlined.Add,
                                contentDescription = "Add Dialog",
                            )
                        }
                    }
                ) {
                    // onClick listener
                    isExpanded = !isExpanded
                }
            }
        }
    ) {
        scaffoldContents()
    }

    ShowIncomeAlertDialog(
        showIncomeDialog,
        incomeState = incomeState,
        incomeDescState = incomeDescState,
        dayOfWeekState = dayOfWeekState,
        dayState = dayState,
        monthState = monthState,
        yearState = yearState,
        incomeViewModel = incomeViewModel
    )

    ShowDebtAlertDialog(
        showDebtDialog,
        debtState = debtState,
        debtDescState = debtDescState,
        dayOfWeekState = dayOfWeekState,
        dayState = dayState,
        monthState = monthState,
        yearState = yearState,
        incomeViewModel = incomeViewModel
    )

    ShowLendAlertDialog(
        showLendDialog,
        lendState = lendState,
        lendDescState = lendDescState,
        dayOfWeekState = dayOfWeekState,
        dayState = dayState,
        monthState = monthState,
        yearState = yearState,
        incomeViewModel = incomeViewModel
    )

    ShowExpenseAlertDialog(
        showExpenseDialog,
        expenseState = expenseState,
        expenseDescState = expenseDescState,
        dayOfWeekState = dayOfWeekState,
        dayState = dayState,
        monthState = monthState,
        yearState = yearState,
        incomeViewModel = incomeViewModel
    )

    ModelDrawerContents(
        isBottomSheetOpen = isBottomSheetOpen,
    )
}

/*
* Clickable Circle float action button
*/
@Composable
fun FloatActionButton(
    containerColor: Color? = MaterialTheme.colorScheme.primaryContainer,
    text: String? = null,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.CenterEnd)
    ) {
        if (text != null)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(60.dp)
            ) {
                Text(text = text)
            }
        Spacer(modifier = Modifier.width(5.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(
                onClick = { onClick() },
                shape = RoundedCornerShape(50),
                containerColor = containerColor!!
            ) {
                icon()
            }

        }
    }
}

