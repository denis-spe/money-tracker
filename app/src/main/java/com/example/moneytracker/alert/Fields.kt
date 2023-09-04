package com.example.moneytracker.alert

import CurrencySymbolByLocation
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AmountTextField(
    state: MutableState<TextFieldValue>,
    label: String,
    placeholder: String){
    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder, fontSize = 20.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = {
            CurrencySymbolByLocation()
        },
        textStyle = TextStyle(fontSize = 20.sp)

    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun DescriptionTextField(
    state: MutableState<TextFieldValue>,
    placeholder: String
){
    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
        },
        label = { Text(text = "Description") },
        placeholder = { Text(text = placeholder, fontSize = 20.sp) },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Info, contentDescription = "description")
        },
        textStyle = TextStyle(fontSize = 20.sp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}