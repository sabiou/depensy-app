package com.godilabs.depensy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.data.model.BudgetPeriod
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.viewmodel.BudgetViewModel
import com.godilabs.depensy.viewmodel.ExpenseViewModel
import com.godilabs.depensy.utils.CategoryUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    viewModel: BudgetViewModel,
    expenseViewModel: ExpenseViewModel
) {
    val budgets by viewModel.budgets.collectAsState()
    val expenses by expenseViewModel.expenses.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Budget",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un budget")
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        if (budgets.isEmpty() && !showAddDialog) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Aucun budget créé.",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { showAddDialog = true }) {
                    Text("Créer un budget")
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(Modifier.height(16.dp)) }

                items(budgets) { budget ->
                    val spent = if (budget.period == null) {
                        // Event-based budget: filter by budgetId
                        expenses.filter { it.budgetId == budget.id }.sumOf { it.amount }.toInt()
                    } else {
                        // Periodic budget: filter by date range
                        budget.startDate?.let { start ->
                            val end = when (budget.period) {
                                BudgetPeriod.DAILY -> start + 86_400_000L
                                BudgetPeriod.WEEKLY -> start + 7 * 86_400_000L
                                BudgetPeriod.MONTHLY -> start + 30 * 86_400_000L // Approx.
                                BudgetPeriod.YEARLY -> start + 365 * 86_400_000L
                            }
                            expenses.filter { expense ->
                                expense.date.time in start until end
                            }.sumOf { it.amount }.toInt()
                        } ?: 0
                    }

                    BudgetOverviewCard(
                        totalBudget = budget.amount.toInt(),
                        spentAmount = spent,
                        remainingAmount = (budget.amount - spent).toInt(),
                        period = budget.period?.displayName ?: budget.name
                    )
                }

                item { Spacer(Modifier.height(80.dp)) }
            }
        }

        if (showAddDialog) {
            AddBudgetDialog(onDismiss = {
                showAddDialog = false
            }) { name, amount, period, category ->
                val budget = Budget(
                    name = name,
                    amount = amount.toDouble(),
                    period = period,
                    category = category,
                    startDate = System.currentTimeMillis()
                )
                viewModel.insert(budget)
                showAddDialog = false
            }
        }
    }
}

@Composable
private fun BudgetOverviewCard(
    totalBudget: Int,
    spentAmount: Int,
    remainingAmount: Int,
    period: String
) {
    if (totalBudget <= 0) return
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Budget Total ($period)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "%,d F".format(totalBudget),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(Modifier.height(24.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { (spentAmount.toFloat() / totalBudget.toFloat()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF4ECDC4),
                trackColor = Color.White.copy(alpha = 0.3f)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Dépensé",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "%,d F".format(spentAmount),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Restant",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "%,d F".format(remainingAmount),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun AddBudgetDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Int, BudgetPeriod?, ExpenseCategory?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedPeriod by remember { mutableStateOf<BudgetPeriod?>(BudgetPeriod.MONTHLY) }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var isPeriodic by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouveau budget", fontWeight = FontWeight.Bold) },
        confirmButton = {
            Button(
                onClick = {
                    amount.toIntOrNull()?.let {
                        onAdd(name, it, if (isPeriodic) selectedPeriod else null, selectedCategory)
                    }
                },
                enabled = name.isNotBlank() && amount.toIntOrNull() != null
            ) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom du budget") }
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Montant") }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isPeriodic, onCheckedChange = { isPeriodic = it })
                    Text("Budget périodique")
                }
                if (isPeriodic) {
                    DropdownMenuField(
                        selected = selectedPeriod,
                        onSelect = { selectedPeriod = it }
                    )
                }
                CategorySelector(
                    selected = selectedCategory,
                    onSelect = { selectedCategory = it }
                )
            }
        }
    )
}

@Composable
private fun DropdownMenuField(selected: BudgetPeriod?, onSelect: (BudgetPeriod) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Période") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            BudgetPeriod.values().forEach { period ->
                DropdownMenuItem(text = {
                    Text(
                        period.name.lowercase().replaceFirstChar { it.uppercase() })
                }, onClick = {
                    onSelect(period); expanded = false
                })
            }
        }
    }
}

@Composable
private fun CategorySelector(
    selected: ExpenseCategory?,
    onSelect: (ExpenseCategory?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected?.let { CategoryUtils.getCategoryLabel(it) } ?: "Toutes les catégories",
            onValueChange = {},
            readOnly = true,
            label = { Text("Catégorie") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Toutes les catégories") },
                onClick = {
                    onSelect(null)
                    expanded = false
                }
            )
            ExpenseCategory.values().forEach { category ->
                DropdownMenuItem(
                    text = { Text(CategoryUtils.getCategoryLabel(category)) },
                    onClick = {
                        onSelect(category)
                        expanded = false
                    }
                )
            }
        }
    }
}