package com.example.moneytracker.pages.loginPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moneytracker.components.AuthButton
import com.example.moneytracker.components.EmailTextField
import com.example.moneytracker.components.PasswordTextField
import com.example.moneytracker.components.validateEmail
import com.example.moneytracker.components.validatePassword
import com.example.moneytracker.pages.PagingDataclass
import com.example.moneytracker.services.auth.AuthorisationImpl
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginPage(
    controller: NavHostController,
    auth: FirebaseAuth
) {
    val pagingDataclass = PagingDataclass()

    // Instantiate the Authorisation object
    val authorisation = AuthorisationImpl(
        auth = auth,
        context = LocalContext.current
    )

    /*
    Email states
     */
    val email = remember {
        mutableStateOf(TextFieldValue())
    }
    val emailWarningMessageState = remember {
        mutableStateOf("")
    }

    val emailIsErrorState = remember {
        mutableStateOf(false)
    }


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

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            "Login",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            "with your Money Tracker account",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(70.dp))

        /*
        Email Input Text Field
         */
        EmailTextField(
            inputState = email,
            label = "Email",
            warningMessage = emailWarningMessageState,
            isError = emailIsErrorState,
        )

        Spacer(modifier = Modifier.height(pagingDataclass.registerHeightSpace))

        /*
        Password Input Text Field
         */
        PasswordTextField(
            inputState = password,
            label = "Password",
            warningMessage = passwordWarningMessageState,
            isError = passwordIsErrorState
        )

        Spacer(modifier = Modifier.height(pagingDataclass.registerHeightSpace))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth(0.78f)
        ){
            /*
            Authorisation Clickable button
            */
            AuthButton(
                text = "Next"
            ) {

                // Assign the names to the variables
                val emailAsString = email.value.text.trim()
                val passwordAsString = password.value.text.trim()

                // Validate the email address
                emailWarningMessageState.value = validateEmail(emailAsString)
                // true if emailWarningMessageState is not empty else false
                emailIsErrorState.value = emailWarningMessageState.value.isNotEmpty()

                // Validate the password address
                passwordWarningMessageState.value = validatePassword(passwordAsString)
                // true if passwordWarningMessageState is not empty else false
                passwordIsErrorState.value = passwordWarningMessageState.value.isNotEmpty()

                if (emailWarningMessageState.value.isEmpty() &&
                    passwordWarningMessageState.value.isEmpty()){
                    authorisation.signIn(
                        emailAsString,
                        password = passwordAsString
                    ){
                        controller.navigate("UserStartUpPage")
                    }
                }
            }
        }
    }
}