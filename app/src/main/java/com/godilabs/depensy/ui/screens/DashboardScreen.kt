package com.godilabs.depensy.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godilabs.depensy.data.model.Expense
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.utils.CategoryUtils
import com.godilabs.depensy.utils.PreferencesManager
import com.godilabs.depensy.viewmodel.ExpenseViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: ExpenseViewModel,
    onAddClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expenses by viewModel.expenses.collectAsState()
    val context = LocalContext.current
    val username = remember { PreferencesManager(context).getUsername() }
    val dateFormat = remember { SimpleDateFormat("dd MMM", Locale.FRENCH) }
    val fullDateFormat = remember { SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH) }
    var isRefreshing by remember { mutableStateOf(false) }
    var isAmountVisible by remember { mutableStateOf(true) }

    // Number formatter for thousands separator
    val numberFormatter = remember {
        DecimalFormat("#,###", DecimalFormatSymbols(Locale.FRENCH).apply {
            groupingSeparator = ' '
        })
    }

    // Calculate today's expenses
    val today = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    val todayExpenses = remember(expenses) {
        expenses.filter { expense ->
            val expenseDate = Calendar.getInstance().apply { time = expense.date }
            val todayDate = Calendar.getInstance().apply { time = today }
            expenseDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR) &&
                    expenseDate.get(Calendar.DAY_OF_YEAR) == todayDate.get(Calendar.DAY_OF_YEAR)
        }
    }

    // Calculate this month's expenses
    val thisMonthExpenses = remember(expenses) {
        expenses.filter { expense ->
            val expenseDate = Calendar.getInstance().apply { time = expense.date }
            val thisMonthDate = Calendar.getInstance()
            expenseDate.get(Calendar.YEAR) == thisMonthDate.get(Calendar.YEAR) &&
                    expenseDate.get(Calendar.MONTH) == thisMonthDate.get(Calendar.MONTH)
        }
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

            Text(
                text = "Bienvenue, $username",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black,
                letterSpacing = 0.1.sp
            )

            Spacer(Modifier.height(32.dp))

            // Horizontal list of expense cards
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    // Today's expenses card
                    ExpenseCard(
                        title = "Dépenses de ce jour",
                        amount = todayExpenses.sumOf { it.amount }.toInt(),
                        isAmountVisible = isAmountVisible,
                        onToggleVisibility = { isAmountVisible = !isAmountVisible },
                        numberFormatter = numberFormatter,
                        modifier = Modifier.width(320.dp)
                    )
                }
                item {
                    // Monthly expenses card
                    ExpenseCard(
                        title = "Dépenses du mois",
                        amount = thisMonthExpenses.sumOf { it.amount }.toInt(),
                        isAmountVisible = isAmountVisible,
                        onToggleVisibility = { isAmountVisible = !isAmountVisible },
                        numberFormatter = numberFormatter,
                        modifier = Modifier.width(320.dp)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Recent expenses header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dépenses récentes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                TextButton(onClick = onViewMoreClick) {
                    Text(
                        text = "Voir plus",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Expenses list
            if (expenses.isEmpty()) {
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
                            text = "Aucune dépense pour le moment",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = onAddClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text(
                                text = "Ajouter une dépense",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(expenses.sortedByDescending { it.date }.take(10)) { expense ->
                        ExpenseItem(
                            expense = expense,
                            dateFormat = dateFormat
                        )
                    }
                }
            }
        }

        // Floating action button
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color.Black,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ajouter une dépense",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ExpenseCard(
    title: String,
    amount: Int,
    isAmountVisible: Boolean,
    onToggleVisibility: () -> Unit,
    numberFormatter: DecimalFormat,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(8.dp))
                if (isAmountVisible) {
                    val formattedAmount = numberFormatter.format(amount)
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                    ) {
                        Text(
                            text = formattedAmount,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = (-1).sp
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "F",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                } else {
                    Text(
                        text = "••••••••",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = (-1).sp
                    )
                }
            }

            IconButton(
                onClick = {
                    onToggleVisibility()
                    performHaptic(context)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isAmountVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isAmountVisible) "Masquer le montant" else "Afficher le montant",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@RequiresPermission(android.Manifest.permission.VIBRATE)
private fun performHaptic(context: Context) {
    val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.EFFECT_TICK))
    } else {
        @Suppress("DEPRECATION")
        v.vibrate(35)
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    dateFormat: SimpleDateFormat
) {
    val numberFormatter = remember {
        DecimalFormat("#,###", DecimalFormatSymbols(Locale.FRENCH).apply {
            groupingSeparator = ' '
        })
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        CategoryUtils.getCategoryColor(expense.category).copy(alpha = 0.12f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = CategoryUtils.getCategoryEmoji(expense.category),
                    fontSize = 26.sp
                )
            }

            Spacer(Modifier.width(16.dp))

            // Middle section: Name, note, and date
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (expense.name.isNotBlank()) {
                    Text(
                        text = expense.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                }

                if (expense.note.isNotBlank()) {
                    Text(
                        text = expense.note,
                        fontSize = 13.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                }

                Text(
                    text = dateFormat.format(expense.date),
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Medium
                )
            }

            // Right side: Amount with styling
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = numberFormatter.format(expense.amount.toInt()),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "F",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }
        }

        // Very thin separator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.Black.copy(alpha = 0.08f))
                .padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseItemPreview() {
    val sampleExpense = Expense(
        id = 0,
        name = "Café Latte Grande",
        amount = 2500.0,
        category = ExpenseCategory.FOOD,
        note = "Starbucks",
        date = Date()
    )
    ExpenseItem(
        expense = sampleExpense,
        dateFormat = SimpleDateFormat("dd MMM", Locale.FRENCH)
    )
}
