package com.example.resourcehub

import android.content.Context

object AppData {
    val favorites = mutableListOf<Resource>()
    var monthlyIncome: Double = 2400.0
    val expenses = mutableMapOf<String, Double>()
    
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        loadData()
    }

    private fun loadData() {
        favorites.clear()
        favorites.addAll(Storage.loadFavorites(appContext))
        
        monthlyIncome = Storage.loadIncome(appContext)
        
        expenses.clear()
        expenses.putAll(Storage.loadExpenses(appContext))
    }

    fun saveFavorites() {
        Storage.saveFavorites(appContext, favorites)
    }

    fun saveBudgetData() {
        Storage.saveBudgetData(appContext, monthlyIncome, expenses)
    }
}
