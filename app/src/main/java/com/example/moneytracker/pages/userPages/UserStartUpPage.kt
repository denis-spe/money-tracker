package com.example.moneytracker.pages.userPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.moneytracker.services.auth.AuthorisationImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun UserStartUpPage(
    controller: NavHostController,
    auth: FirebaseAuth,
    currentUser: FirebaseUser?
){
    // Instantiate the Authorisation object
    val authorisation = AuthorisationImpl(
        auth = auth,
        context = LocalContext.current
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome back ${auth.currentUser?.displayName}")
        Button(onClick = {
            controller.navigate("StartUpPage")
            authorisation.signOut()
        }) {
            Text(text = "Sign out")
        }
    }
}