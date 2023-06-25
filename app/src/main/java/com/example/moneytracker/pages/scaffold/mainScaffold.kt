package com.example.moneytracker.pages.scaffold

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.moneytracker.alert.ShowAlertDialogWithTextField
import com.example.moneytracker.models.EarnedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldComponent(
    showDialog: MutableState<Boolean>,
    earnedViewModel: EarnedViewModel,
    ScaffoldContents: @Composable () -> Unit
){

    val earnedState = remember { mutableStateOf(TextFieldValue()) }
    val dayOfWeekState = remember { mutableStateOf(TextFieldValue()) }
    val dayState = remember { mutableStateOf(TextFieldValue()) }
    val monthState = remember { mutableStateOf(TextFieldValue()) }
    val yearState = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {

            FloatingActionButton(
                onClick = { showDialog.value = true },
                shape = FloatingActionButtonDefaults.shape
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add Dialog",
                )
            }
        }
    ) {
        ScaffoldContents()
    }

    ShowAlertDialogWithTextField(
        showDialog,
        earnedState = earnedState,
        dayOfWeekState = dayOfWeekState,
        dayState = dayState,
        monthState = monthState,
        yearState = yearState,
        earnedViewModel = earnedViewModel
    )
}

