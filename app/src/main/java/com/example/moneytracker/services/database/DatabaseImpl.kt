package com.example.moneytracker.services.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class DatabaseImpl(
    private val firestore: FirebaseFirestore
): Database {

    // Data class
    private val dataclass = Dataclass()

    /**
     * @author denis-spe
     *
     * Create data field
     * @return None
     */
    override fun createDataField(
        email: String
    ) {
        // create database object
        val database = DatabaseDataclass()

        firestore.collection(dataclass.databaseName)
            .document(email)
            .set(database)
            .addOnSuccessListener {
                Log.d(TAG, "Creation of firestore has been success")
            }.addOnFailureListener {
                Log.w(TAG, "Error: $it")
            }
    }

    /**
     * @author denis-spe
     *
     * Add value to the field array
     * @param email of the user
     * @param fieldName name of the field to modify
     * @param value to add the field
     * @return None
     */
    override fun addValue(
        email: String,
        fieldName: String,
        value: Pair<String, Double>
    ) {
        firestore.collection(dataclass.databaseName)
            .document(email)
            .update(fieldName, FieldValue.arrayUnion(value))
    }

    /**
     * @author denis-spe
     *
     * Fetch the data from firestore
     * @param email of the user
     * @param getData lambda function provides data
     * @return None
     */
    override fun fetchData(
        email: String,
        getData: (data: MutableMap<String, Any>) -> Unit
    ) {
        firestore.collection(dataclass.databaseName)
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    if (document.id == email){
                        getData(document.data)
                    }
                }
            }
    }

    /**
     * @author denis-spe
     *
     * Fetch the live the data from firestore
     * @param email of the user
     * @param getData lambda function provides data
     * @return None
     */
    override fun fetchLiveData(
        email: String,
        getData: (data: MutableMap<String, Any>) -> Unit
    ) {
        firestore.collection(dataclass.databaseName)
            .addSnapshotListener { querySnapshot: QuerySnapshot?,
                                   _: FirebaseFirestoreException? ->
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        if (document.id == email) {
                            getData(document.data)
                        }
                    }
                }
            }
    }
}