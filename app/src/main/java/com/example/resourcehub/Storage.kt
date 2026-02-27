package com.example.resourcehub

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

object Storage {
    private const val PREFS = "resourcehub_prefs"
    private const val KEY_FAVORITES = "favorites"
    private const val KEY_INCOME = "monthly_income"
    private const val KEY_EXPENSES = "expenses"

    private val gson = Gson()

    // ---------- Favorites ----------
    fun saveFavorites(context: Context, favorites: List<Resource>) {
        val json = gson.toJson(favorites)
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_FAVORITES, json)
            }
    }

    fun loadFavorites(context: Context): MutableList<Resource> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_FAVORITES, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Resource>>() {}.type
        return gson.fromJson(json, type)
    }

    // ---------- Budget ----------
    fun saveBudgetData(context: Context, income: Double, expenses: Map<String, Double>) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit {
                putFloat(KEY_INCOME, income.toFloat())
                putString(KEY_EXPENSES, gson.toJson(expenses))
            }
    }

    fun loadIncome(context: Context): Double {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_INCOME, 2400.0f).toDouble()
    }

    fun loadExpenses(context: Context): MutableMap<String, Double> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_EXPENSES, null) ?: return mutableMapOf(
            "Rent" to 1200.0,
            "Utilities" to 200.0,
            "Insurance" to 150.0,
            "Credit Cards" to 180.0
        )
        val type = object : TypeToken<MutableMap<String, Double>>() {}.type
        return gson.fromJson(json, type)
    }
}
