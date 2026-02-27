package com.example.resourcehub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class BudgetActivity : AppCompatActivity() {

    private lateinit var txtRemaining: TextView
    private lateinit var txtIncomeDisplay: TextView
    private lateinit var lvExpenses: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        txtRemaining = findViewById(R.id.txtRemainingAmount)
        txtIncomeDisplay = findViewById(R.id.txtMonthlyIncomeDisplay)
        lvExpenses = findViewById(R.id.lvExpenses)

        updateIncomeDisplay()
        updateRemaining()

        findViewById<TextView>(R.id.btnEditIncome).setOnClickListener {
            showEditIncomeDialog()
        }

        refreshExpenseList()

        findViewById<TextView>(R.id.btnAddExpense).setOnClickListener {
            showAddExpenseDialog()
        }

        lvExpenses.setOnItemClickListener { _, _, position, _ ->
            val expenseName = AppData.expenses.keys.toList()[position]
            showEditExpenseDialog(expenseName)
        }

        setupBottomNav()
    }

    private fun updateIncomeDisplay() {
        txtIncomeDisplay.text = String.format(Locale.US, "$%.2f", AppData.monthlyIncome)
        AppData.saveBudgetData()
    }

    private fun updateRemaining() {
        val totalExpenses = AppData.expenses.values.sum()
        val remaining = AppData.monthlyIncome - totalExpenses
        txtRemaining.text = String.format(Locale.US, "$%.2f", remaining)
        AppData.saveBudgetData()
    }

    private fun refreshExpenseList() {
        val expenseStrings = AppData.expenses.map { "${it.key}: $${String.format(Locale.US, "%.2f", it.value)}" }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, expenseStrings)
        lvExpenses.adapter = adapter
    }

    private fun showEditIncomeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Monthly Income")

        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        input.setText(AppData.monthlyIncome.toString())
        builder.setView(input)

        builder.setPositiveButton("Save") { _, _ ->
            AppData.monthlyIncome = input.text.toString().toDoubleOrNull() ?: 0.0
            updateIncomeDisplay()
            updateRemaining()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun showAddExpenseDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Expense")

        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val nameInput = EditText(this)
        nameInput.hint = "Expense Name"
        layout.addView(nameInput)

        val amountInput = EditText(this)
        amountInput.hint = "Amount"
        amountInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(amountInput)

        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val name = nameInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
            if (name.isNotEmpty()) {
                AppData.expenses[name] = amount
                refreshExpenseList()
                updateRemaining()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun showEditExpenseDialog(expenseName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Expense: $expenseName")

        val amountInput = EditText(this)
        amountInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        amountInput.setText(AppData.expenses[expenseName].toString())
        builder.setView(amountInput)

        builder.setPositiveButton("Save") { _, _ ->
            val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
            AppData.expenses[expenseName] = amount
            refreshExpenseList()
            updateRemaining()
        }
        builder.setNegativeButton("Delete") { _, _ ->
            AppData.expenses.remove(expenseName)
            refreshExpenseList()
            updateRemaining()
        }
        builder.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun setupBottomNav() {
        findViewById<TextView>(R.id.navHomeBudget).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<TextView>(R.id.navFavoritesBudget).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        findViewById<TextView>(R.id.navBudgetBudget).setOnClickListener {
            // Already on Budget
        }
        findViewById<TextView>(R.id.navProfileBudget).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
