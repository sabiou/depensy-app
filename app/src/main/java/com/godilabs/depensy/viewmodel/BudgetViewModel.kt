package com.godilabs.depensy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.data.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(private val repository: BudgetRepository) : ViewModel() {

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allBudgets.collect { budgetList ->
                _budgets.value = budgetList
            }
        }
    }

    fun insert(budget: Budget) = viewModelScope.launch {
        repository.insert(budget)
    }

    fun update(budget: Budget) = viewModelScope.launch {
        repository.update(budget)
    }

    fun delete(budget: Budget) = viewModelScope.launch {
        repository.delete(budget)
    }
}