package com.example.moneytracker.services.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthorisationImpl(
    private val auth: FirebaseAuth,
    private val context: Context
): Authorisation{

    /**
     * @author denis-spe
     *
     * Sign in the user
     * @param email of the user
     * @param password of the user
     * @param onSuccessListener do something if it successes
     * @return None
     */
    override fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {task ->
            if (task.isSuccessful){

                // Show some message
                Log.d(TAG, "Signing in has been successfully")

                // Do something
                onSuccessListener()
            } else {

                // show some message
                Log.d(TAG, "Signing in wasn't success")

                // Alert the unsuccessful message
                Toast.makeText(
                    context,
                    "Failed To Sign In",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * @author denis-spe
     *
     * Sign out the user
     * @return None
     */
    override fun signOut() {
        auth.signOut()
    }

    /**
     * @author denis-spe
     *
     * Register the user
     * @param firstName of the user
     * @param lastName of the user
     * @param email of the user
     * @param password of the user
     * @param onSuccessListener do something if it successes
     * @return None
     */
    override fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {task ->
            if (task.isSuccessful){
                // Show the message
                Log.d(TAG, "Successfully registered")

                // Add profile name
                val profile = UserProfileChangeRequest
                    .Builder()
                    .setDisplayName("$firstName $lastName")
                    .build()

                // Update the user profile
                task.result.user?.updateProfile(profile)

                // Do something
                onSuccessListener()
            } else {
                // Show the message
                Log.d(TAG, "Failed to register")

                // Alert the unsuccessful message
                Toast.makeText(
                    context,
                    "Failed To Register",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.addOnFailureListener { e ->
            Log.w(TAG, "Failed with this error: $e")
        }
    }
}