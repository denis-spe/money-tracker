package com.example.moneytracker.pages.pagingManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.pages.loginPage.LoginPage
import com.example.moneytracker.pages.registerPage.EmailRegistrationPage
import com.example.moneytracker.pages.registerPage.NamesRegistrationPage
import com.example.moneytracker.pages.registerPage.PasswordRegistrationPage
import com.example.moneytracker.pages.startUpPage.StartUpPage
import com.example.moneytracker.pages.userPages.UserStartUpPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun PagingManager(
    auth: FirebaseAuth,
    currentUser: FirebaseUser?
) {
    // Initializer the rememberNavController
    val controller = rememberNavController()

    /*
    Text field states
     */
    val firstName = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val lastName = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val email = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val password = remember {
        mutableStateOf(TextFieldValue(""))
    }

    // Make a paging host
    NavHost(
        navController = controller,
        startDestination = if (currentUser == null) "StartUpPage"
                           else "UserStartUpPage"
    ){
        /**
         * Start up page
          */
        composable("StartUpPage"){
            StartUpPage(controller)
        }

        /**
         * *********** Registration Pages *********
         */
        // Names registration page router
        composable("NamesRegistrationPage"){
            NamesRegistrationPage(
                controller,
                firstName = firstName,
                lastName = lastName
                )
        }

       // Email registration page router
        composable("EmailRegistrationPage"){
            EmailRegistrationPage(
                controller,
                email = email
                )
        }


        // Password registration page router
        composable("PasswordRegistrationPage"){
            PasswordRegistrationPage(
                controller,
                auth = auth,
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }

        /**
         * ************ Login page **********
         */
        // Login page router
        composable("LoginPage"){
            LoginPage(
                controller,
                auth = auth
            )
        }

        /**
        *  ************ User start up page registration page ********
         */
        composable("UserStartUpPage"){
            UserStartUpPage(
                controller,
                auth = auth,
                currentUser = currentUser
            )
        }
    }
}