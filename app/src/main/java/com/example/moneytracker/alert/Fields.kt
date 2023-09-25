package com.example.moneytracker.alert

import CurrencySymbolByLocation
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun formatWithSpaces(text: String): String {
    val digitsAndDecimalOnly = text.filter { it.isDigit() || it == '.' }


    var spacedText = buildString {

        for ((index, char) in digitsAndDecimalOnly.withIndex()) {
            append(char)

            if (
                (index % 3 == 0) &&
                index < digitsAndDecimalOnly.length - 1
            ) {
                append(' ') // Add space every 4 digits (adjust as needed)
            }
        }
    }
    return spacedText
}

//fun formatWithSpaces(text: String): String {
//    val cleanedText = text.filter { it.isDigit() || it == '.' }
//    val parts = cleanedText.split(".")
//    val integerPart = parts.getOrNull(0) ?: ""
//    val decimalPart = parts.getOrNull(1) ?: ""
//
//    val integerWithSpaces = integerPart.chunked(3).joinToString(" ") { it }
//
//    return if (decimalPart.isNotEmpty()) {
//        "$integerWithSpaces.$decimalPart"
//    } else {
//        integerWithSpaces
//    }
//}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AmountTextField(
    state: MutableState<TextFieldValue>,
    label: String,
    placeholder: String
) {

    TextField(
        value = state.value,
        onValueChange = { newValue ->

            var formattedText = formatWithSpaces(newValue.text)
                .replace("\\.\\s|\\s\\.".toRegex(), ".")

            // Replace spaces after a dot
            if (formattedText.contains('.')) {
                var splitText = formattedText.split(".")
                formattedText = "${splitText[0]}.${splitText[1].replace(" ", "")}"
            }

            // Replacing according to the number whole number
            formattedText = if (
                formattedText.length <= 4 ||
                ((formattedText.substringBefore(".").length < 5) &&
                        formattedText.contains(".")
                        )
            )
                formattedText.replace(" ", "")
            else
                formattedText

            // Placing the space on the third index in the string
            formattedText = (
                    if (
                        formattedText.length == 7 && !(formattedText.contains("."))
                        ){
                        var text = formattedText.replace(" ", "")
                        text.substring(0, 2) + " ${text.substring(2, text.length)}"
                    }
                    else if (
                        formattedText.substringBefore(".").length in 7..8
                        && formattedText.contains(".")
                    ){
                        var text = formattedText.replace(" ", "")
                        text.substring(0, 2) + " ${text.substring(2, text.length)}"
                    }
                    else if (
                        formattedText.substringBefore(".").length == 7
                        && formattedText.contains(".")
                    ){
                        var text = formattedText.replace(" ", "")
                        text.substring(0, 1) + " ${text.substring(1, text.length)}"
                    }
                    else
                        formattedText
                    )

            // Limit the inputs to 21
            if ((formattedText.contains("."))){
                if (formattedText.length < 20)
                    state.value = TextFieldValue(
                        text = formattedText,
                        selection = TextRange(formattedText.length) // Set cursor at the end
                    )
            }
            else{
                if (formattedText.length < 22)
                    state.value = TextFieldValue(
                        text = formattedText,
                        selection = TextRange(formattedText.length) // Set cursor at the end
                    )
            }



        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder, fontSize = 20.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        leadingIcon = {
            CurrencySymbolByLocation()
        },
        textStyle = TextStyle(fontSize = 20.sp),
        visualTransformation = VisualTransformation.None
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun DescriptionTextField(
    state: MutableState<TextFieldValue>,
    placeholder: String
) {
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