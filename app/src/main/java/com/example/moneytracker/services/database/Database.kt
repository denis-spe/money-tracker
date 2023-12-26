package com.example.moneytracker.services.database

/**
 * Cloud database in
 */
interface Database {
    /**
     * @author denis-spe
     *
     * Create data field
     * @param email of the user
     * @return None
     */
    fun createDataField(
        email: String
    )

    /**
     * @author denis-spe
     *
     * Add value to the field array
     * @param email of the user
     * @param fieldName name of the field to modify
     * @param value to add the field
     * @return None
     */
    fun addValue(
        email: String,
        fieldName: String,
        value: Pair<String, Double>
    )

    /**
     * @author denis-spe
     *
     * Fetch the data from firestore
     * @param email of the user
     * @param getData lambda function provides data
     * @return None
     */
    fun fetchData(
        email: String,
        getData: (data: MutableMap<String, Any>) -> Unit
    )

    /**
     * @author denis-spe
     *
     * Fetch the live the data from firestore
     * @param email of the user
     * @param getData lambda function provides data
     * @return None
     */
    fun fetchLiveData(
        email: String,
        getData: (data: MutableMap<String, Any>) -> Unit
    )
}