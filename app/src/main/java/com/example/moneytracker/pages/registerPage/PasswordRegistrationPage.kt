package com.example.moneytracker.pages.registerPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.pages.PagingDataclass
import com.example.moneytracker.components.AuthButton
import com.example.moneytracker.components.PasswordTextField
import com.example.moneytracker.components.validatePassword
import com.example.moneytracker.services.auth.AuthorisationImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@Composable
fun PasswordRegistrationPage(
    controller: NavHostController,
    auth: FirebaseAuth,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    email: MutableState<TextFieldValue>
) {
    val pagingDataclass = PagingDataclass()

    // Instantiate the Authorisation object
    val authorisation = AuthorisationImpl(
        auth,
        context = LocalContext.current
    )

    /*
    Password states
     */
    val password = remember {
        mutableStateOf(TextFieldValue())
    }

    val passwordWarningMessageState = remember {
        mutableStateOf("")
    }

    val passwordIsErrorState = remember {
        mutableStateOf(false)
    }

    /*
    Confirm Password states
     */
    val confirmPassword = mutableStateOf(TextFieldValue())

    val confirmPasswordWarningMessageState = remember {
        mutableStateOf("")
    }

    val confirmPasswordIsErrorState = remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Money Tracker",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            "Create a strong password",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            "Create a strong password with a mix of letters,",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            "numbers and symbols",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(40.dp))

        /*
        Password Input Text Field
         */
        PasswordTextField(
            inputState = password,
            label = "Password",
            isError = passwordIsErrorState,
            warningMessage = passwordWarningMessageState
        )

        Spacer(modifier = Modifier.height(pagingDataclass.registerHeightSpace))

        /*
        Last Name Input Text Field
         */
        PasswordTextField(
            inputState = confirmPassword,
            label = "Confirm password",
            isError = confirmPasswordIsErrorState,
            warningMessage = confirmPasswordWarningMessageState
        )



        Spacer(modifier = Modifier.height(pagingDataclass.registerHeightSpace))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth(0.78f)
        ) {
            /*
            Authorisation Clickable button
            */
            AuthButton(
                text = "Next"
            ) {
                // Assign the password to the new variables
                val passwordAsString = password.value.text
                val confirmPasswordAsString = confirmPassword.value.text

                // Validate the password address
                passwordWarningMessageState.value = validatePassword(passwordAsString)
                // true if passwordWarningMessageState is not empty else false
                passwordIsErrorState.value = passwordWarningMessageState.value.isNotEmpty()

                // Validate the password address
                confirmPasswordWarningMessageState.value = when {
                    confirmPasswordAsString.isEmpty() -> "please confirm your password"
                    passwordWarningMessageState.value.isNotEmpty() ->
                        passwordWarningMessageState.value
                    passwordAsString != confirmPasswordAsString -> "password doesn't match"
                    else -> ""
                }
                // true if passwordWarningMessageState is not empty else false
                confirmPasswordIsErrorState.value = confirmPasswordWarningMessageState.value.isNotEmpty()

                if (passwordWarningMessageState.value.isEmpty()
                    && confirmPasswordWarningMessageState.value.isEmpty())
                    authorisation.register(
                        firstName = firstName.value.text,
                        lastName = lastName.value.text,
                        email = email.value.text,
                        password = passwordAsString
                    ) {
                        controller.navigate("UserStartUpPage")
                    }
            }
        }
    }
}