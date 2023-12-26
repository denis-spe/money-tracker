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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moneytracker.components.AuthButton
import com.example.moneytracker.components.NameTextField
import com.example.moneytracker.components.validateName
import com.example.moneytracker.pages.PagingDataclass
import com.example.moneytracker.services.auth.AuthorisationImpl
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NamesRegistrationPage(
    controller: NavHostController,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>
) {
    val pagingDataclass = PagingDataclass()

    /*
    First name states
     */
    val firstNameWarningMessageState = remember {
        mutableStateOf("")
    }

    val firstNameIsErrorState = remember {
        mutableStateOf(false)
    }

    /*
    Last name state
     */
    val lastNameWarningMessageState = remember {
        mutableStateOf("")
    }

    val lastNameIsErrorState = remember {
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

        Text(
            "Create a Money Tracker Account",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(70.dp))

        /*
        First Name Input Text Field
         */
        NameTextField(
            inputState = firstName,
            label = "First name",
            warningMessage = firstNameWarningMessageState,
            isError = firstNameIsErrorState
        )

        Spacer(modifier = Modifier.height(pagingDataclass.registerHeightSpace))

        /*
        Last Name Input Text Field
         */
        NameTextField(
            inputState = lastName,
            label = "Last name (Optional)",
            warningMessage = lastNameWarningMessageState,
            isError = lastNameIsErrorState
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
                // Get first and last name as a string
                val firstNameAsString = firstName.value.text
                val lastNameAsString = lastName.value.text

                // Validate the first address
                firstNameWarningMessageState.value = validateName(firstNameAsString)
                // true if firstNameIsErrorState is not empty else false
                firstNameIsErrorState.value = firstNameWarningMessageState.value.isNotEmpty()

                // Validate the last name address
                lastNameWarningMessageState.value = when{
                    lastNameAsString.isEmpty() -> ""
                    lastNameAsString.isNotEmpty() -> validateName(lastNameAsString)
                    else -> ""
                }
                // true if lastNameIsErrorState is not empty else false
                lastNameIsErrorState.value = lastNameWarningMessageState.value.isNotEmpty()

                if (firstNameWarningMessageState.value.isEmpty()
                    && firstNameWarningMessageState.value.isEmpty()
                    )
                    controller.navigate("EmailRegistrationPage");
            }
        }
    }
}