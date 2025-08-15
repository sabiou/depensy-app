package com.godilabs.depensy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: ExpenseViewModel,
    modifier: Modifier = Modifier
) {
    val expensesState = viewModel.expenses.collectAsState()
    val expenses = expensesState.value
    var selectedCategory: ExpenseCategory? by remember { mutableStateOf(null) }
    var showMenu: Boolean by remember { mutableStateOf(false) }

    val dateFormat = remember { SimpleDateFormat("dd MMM", Locale.FRENCH) }

    val filteredExpenses = remember(expenses, selectedCategory) {
        selectedCategory?.let { cat ->
            expenses.filter { it.category == cat }
        } ?: expenses
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Historique",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black,
                    letterSpacing = 0.1.sp
                )

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrer")
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(text = { Text("Tous") }, onClick = {
                            selectedCategory = null; showMenu = false
                        })
                        ExpenseCategory.values().forEach { category ->
                            DropdownMenuItem(text = { Text(category.displayName) }, onClick = {
                                selectedCategory = category; showMenu = false
                            })
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            if (filteredExpenses.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (selectedCategory != null)
                                "Aucune dépense trouvée pour ${selectedCategory!!.displayName.lowercase()}"
                            else
                                "Aucune dépense enregistrée",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredExpenses.sortedByDescending { it.date }) { expense ->
                        ExpenseItem(expense = expense, dateFormat = dateFormat)
                    }
                }
            }
        }
    }
}
