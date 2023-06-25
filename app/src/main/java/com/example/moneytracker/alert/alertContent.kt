package com.example.moneytracker.alert

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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

        Button(onClick = { calenderContent(
            mContext,
            dayOfWeekState = dayOfWeekState,
            dayState = dayState,
            monthState = monthState,
            yearState = yearState,
        ).show() }) {
            Row {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date range")
                Spacer(modifier = Modifier.width(5.dp))
                val day = dayState.value.text
                if (day == "")
                    Text(text = "Select date");
                else
                    Text(
                        text = "${dayOfWeekState.value.text} ${dayState.value.text}" +
                                "/${monthState.value.text}" +
                        "/${yearState.value.text}")
            }
        }

    }
}



