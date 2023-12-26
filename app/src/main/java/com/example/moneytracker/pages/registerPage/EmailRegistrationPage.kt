package com.example.moneytracker.pages.registerPage

import android.provider.ContactsContract.CommonDataKinds.Email
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moneytracker.components.AuthButton
import com.example.moneytracker.components.EmailTextField
import com.example.moneytracker.components.validateEmail
import com.example.moneytracker.pages.PagingDataclass
import com.example.moneytracker.services.auth.AuthorisationImpl
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EmailRegistrationPage(
    controller: NavHostController,
    email: MutableState<TextFieldValue>
) {
    val pagingDataclass = PagingDataclass()

    /*
    Email states
     */
    val emailWarningMessageState = remember {
        mutableStateOf("")
    }

    val emailIsErrorState = remember {
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

        Column(
            modifier = Modifier.fillMaxWidth(0.73f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Enter the email address you want to",
                fontSize = 15.sp,
                fontWeight = FontWeight.W700,
            )

            Text(
                "use for your Money Tracker Account",
                fontSize = 15.sp,
                fontWeight = FontWeight.W700,
            )
            Text(
                "Money Tracker Account",
                fontSize = 15.sp,
                fontWeight = FontWeight.W700
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        /*
        Email Input Text Field
         */
        EmailTextField(
            inputState = email,
            label = "Email",
            isError = emailIsErrorState,
            warningMessage = emailWarningMessageState
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
                // Assign the names to the variables
                val emailAsString = email.value.text

                // Validate the email address
                emailWarningMessageState.value = validateEmail(emailAsString)
                // true if emailWarningMessageState is not empty else false
                emailIsErrorState.value = emailWarningMessageState.value.isNotEmpty()

                if (emailWarningMessageState.value.isEmpty())
                    controller.navigate("PasswordRegistrationPage")
            }
        }
    }
}