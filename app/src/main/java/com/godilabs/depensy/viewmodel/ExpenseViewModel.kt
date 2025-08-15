package com.godilabs.depensy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godilabs.depensy.data.model.Expense
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {
    private val _expenses = repository.getAllExpenses().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val expenses: StateFlow<List<Expense>> = _expenses

    private val _totalAmount = repository.getTotalAmount().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )
    val totalAmount: StateFlow<Double?> = _totalAmount

    fun addExpense(
        amount: Double,
        category: ExpenseCategory,
        date: Date,
        name: String,
        note: String,
        budget: Budget?
    ) {
        viewModelScope.launch {
            repository.insertExpense(
                Expense(
                    amount = amount,
                    category = category,
                    date = date,
                    name = name,
                    note = note,
                    budgetId = budget?.id
                )
            )
        }
    }

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            repository.deleteExpenseById(id)
        }
    }
}