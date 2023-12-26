package com.example.moneytracker.services.auth

/**
 * User authorisation
 */
interface Authorisation {
    /**
     * @author denis-spe
     *
     * Sign in the user
     * @param email of the user
     * @param password of the user
     * @param onSuccessListener do something if it successes
     * @return None
     */
    fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit
    );

    /**
     * @author denis-spe
     *
     * Sign out the user
     * @return None
     */
    fun signOut();

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
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit
    );
}