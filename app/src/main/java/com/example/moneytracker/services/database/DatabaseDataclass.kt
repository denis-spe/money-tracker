package com.example.moneytracker.services.database

data class DatabaseDataclass(
    val debts: MutableMap<String, Double> = mutableMapOf(),
    val expense: MutableMap<String, Double> = mutableMapOf(),
    val income: MutableMap<String, Double> = mutableMapOf(),
    val lend: MutableMap<String, Double> = mutableMapOf(),
)
