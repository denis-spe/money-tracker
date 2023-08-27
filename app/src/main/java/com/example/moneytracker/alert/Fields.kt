package com.example.moneytracker.alert

import CurrencySymbolByLocation
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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
        modifier = Modifier.padding(16.dp),
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = {
            CurrencySymbolByLocation()
        },

        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun PasswordVisualTransformation(): VisualTransformation {
    return VisualTransformation { textSnapshot ->
        TransformedText(textSnapshot, offsetMapping = OffsetMapping.Identity)
    }
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
        modifier = Modifier.padding(16.dp),
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Info, contentDescription = "description")
        }
    )
}