package com.example.moneytracker.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.R
import kotlinx.coroutines.delay


/**
 * Use it to get email address
 * @param inputState text input state
 * @param label text placeholder
 * @param warningMessage error message to shown
 * @param isError changes the field border color to red
 * @return Unit
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun EmailTextField(
    inputState: MutableState<TextFieldValue>,
    label: String,
    warningMessage: MutableState<String>,
    isError: MutableState<Boolean>
){
    var showErrorMessageState by remember {
        mutableStateOf(false)
    }

    // initialize focus reference to be able to request focus programmatically
    val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = inputState.value,
            label = {
                Text(label)
            },
            textStyle = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            ),

            isError = isError.value,

            singleLine = true,

            onValueChange = {
                isError.value = false
                showErrorMessageState = false
                inputState.value = it
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.Email,
                    contentDescription = "$label icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },

            trailingIcon = {
                if (isError.value) {
                    IconButton(onClick = {
                        showErrorMessageState = !showErrorMessageState
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Info,
                            contentDescription = "Info icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth(),
            interactionSource = interactionSource
        )

        // LaunchedEffect prevents endless focus request
        LaunchedEffect(focusRequester) {
            if (isPressed) {
                focusRequester.requestFocus()
                delay(100) // Make sure you have delay here
                keyboard?.show()
            }
        }

        // Show message
        if (showErrorMessageState){
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = warningMessage.value,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

fun validateEmail(input: String): String{
    val regex = """[a-zA-Z]{2,80}([0-9]+)?@(gmail|mail).com""".toRegex()
    return when{
        input.isEmpty() -> "fill in the email address"
        !regex.matches(input) -> "Invalid email address"
        else -> {
            return ""
        }
    }
}


/**
 * Use it to get password
 * @param inputState text input state
 * @param label text placeholder
 * @param warningMessage error message to shown
 * @param isError changes the field border color to red
 * @return Unit
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun PasswordTextField(
    inputState: MutableState<TextFieldValue>,
    label: String,
    warningMessage: MutableState<String>? = null,
    isError: MutableState<Boolean>?
){

    // Display visibility trailing icon if is clicked
    var showPasswordState by rememberSaveable {
        mutableStateOf(true)
    }

    var showErrorMessageState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            label = {
                Text(label)
            },
            value = inputState.value,

            textStyle = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            ),

            isError = isError!!.value,

            singleLine = true,

            onValueChange = {
                showErrorMessageState = false
                isError.value = false
                inputState.value = it
            },

            modifier = Modifier
                .fillMaxWidth(),

            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.Lock,
                    contentDescription = "$label icon",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },

            trailingIcon = {
                if (!isError.value) {
                    IconButton(onClick = {
                        showPasswordState = !showPasswordState
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (showPasswordState) R.drawable.visibility_off  else R.drawable.visibility
                            ),
                            contentDescription = "Show the password",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                else {
                    IconButton(onClick = {
                        showErrorMessageState = !showErrorMessageState
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Info,
                            contentDescription = "Info icon",
                            tint =  MaterialTheme.colorScheme.error
                        )
                    }
                } },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

            visualTransformation = if (showPasswordState) PasswordVisualTransformation()
            else VisualTransformation.None
        )

        // Show message
        if (showErrorMessageState){
            Box(modifier = Modifier.fillMaxWidth()){
                warningMessage?.value?.let { Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                ) }
            }
        }
    }
}

fun validatePassword(input: String): String{
    val limitCharRegex = Regex("""[a-zA-Z0-9]{8,}""")
    val letterOnlyRegex = Regex("""[a-zA-Z0-9]+""")
    return when{
        input.isEmpty() -> "fill in the password"
        !letterOnlyRegex.matches(input) -> "password can not contain symbols"
        !limitCharRegex.matches(input) -> "use 8 and above characters or digits for the password"
        else -> {
            return ""
        }
    }
}


/**
 * Use it to get user name
 * @param inputState text input state
 * @param label text placeholder
 * @param warningMessage error message to shown
 * @param isError changes the field border color to red
 * @return Unit
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun NameTextField(
    inputState: MutableState<TextFieldValue>,
    label: String,
    warningMessage: MutableState<String>? = null,
    isError: MutableState<Boolean>
){
    var showFieldMessageState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            label = {
                Text(label)
            },
            value = inputState.value,

            textStyle = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            ),

            isError = warningMessage?.value?.isNotEmpty() == true,

            singleLine = true,

            onValueChange = {

                inputState.value = it
            },

            modifier = Modifier
                .fillMaxWidth(),

            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.AccountCircle,
                    contentDescription = "$label icon",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },

            trailingIcon = {
                IconButton(onClick = {
                    showFieldMessageState = true
                }) {
                    if (isError.value)
                        Icon(
                            imageVector = Icons.TwoTone.Info,
                            contentDescription = "Info icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                }
           },
        )

        // Show message
        if (showFieldMessageState){
            Box(modifier = Modifier.fillMaxWidth()){
                warningMessage?.value?.let { Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                ) }
            }
        }
    }
}

fun validateName(input: String): String{
    val regex = Regex("""[a-zA-Z0-9]+""")
    return when{
        input.isEmpty() -> "fill in the first name"
        !regex.matches(input) -> "user first name can not contain symbol"
        else -> {
            return ""
        }
    }
}




@Composable
fun AuthButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(size = 5.dp),
    ) {
        Text(text = text)
    }
}
