package com.godilabs.depensy.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godilabs.depensy.data.model.ExpenseCategory
import com.godilabs.depensy.data.model.Budget
import com.godilabs.depensy.viewmodel.ExpenseViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private val cashGreen = Color(0xFF00D632)
private val lightGray = Color(0xFFF9F9F9)
private val buttonGray = Color(0xFFEEEEEE)
private val errorRed = Color(0xFFFF453A)

@RequiresPermission(android.Manifest.permission.VIBRATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: ExpenseViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val today = remember { Date() }
    var step by remember { mutableStateOf(0) } // 0=amount, 1=cat, 2=infos
    var montant by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var date by remember { mutableStateOf(today) }
    var note by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var budgets by remember { mutableStateOf(listOf<Budget>()) }
    var selectedBudget by remember { mutableStateOf<Budget?>(null) }
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.FRENCH) }
    var justAdded by remember { mutableStateOf(false) }

    val stepTitles = listOf("Montant", "Catégorie", "Détails")

    LaunchedEffect(showSuccess, step) {
        if (showSuccess) {
            showSuccess = false
            showSuccessDialog = true
        }
    }

    if (showDatePicker) {
        val cal = Calendar.getInstance().apply { time = date }
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance().apply { set(year, month, dayOfMonth) }.time
                date = newDate
                showDatePicker = false
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnCancelListener { showDatePicker = false }
        }.show()
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false; onBack() },
            containerColor = Color.White,
            titleContentColor = Color.Black,
            textContentColor = Color.Black,
            title = {
                Text(
                    "Opération réussie",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    "La dépense a été ajoutée avec succès.",
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false; onBack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ajouter dépense",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Crossfade(step, label = "steps", animationSpec = tween(400)) { s ->
                when (s) {
                    0 -> AddExpenseStepAmount(
                        montant,
                        onInput = { montant = it },
                        onContinue = {
                            if (montant.toDoubleOrNull() != null && montant.toDouble() > 0.0) {
                                step = 1; showError = false; performHaptic(context)
                            } else {
                                showError = true; performHaptic(context)
                            }
                        },
                        showError = showError
                    )

                    1 -> AddExpenseStepCategory(
                        selectedCategory,
                        onSelect = { selectedCategory = it },
                        onContinue = {
                            if (selectedCategory != null) {
                                step = 2; showError = false; performHaptic(context)
                            } else {
                                showError = true; performHaptic(context)
                            }
                        },
                        onBack = { step = 0 },
                        showError = showError
                    )

                    2 -> AddExpenseStepInfos(
                        date = date,
                        note = note,
                        name = name,
                        budgets = budgets,
                        selectedBudget = selectedBudget,
                        onBudgetSelected = { selectedBudget = it },
                        onNoteChange = { note = it },
                        onNameChange = { name = it},
                        onDateChange = { d -> date = d },
                        onFinish = {
                            if (!isLoading) {
                                isLoading = true
                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        viewModel.addExpense(
                                            montant.toDouble(),
                                            selectedCategory ?: ExpenseCategory.FOOD,
                                            date, name, note, selectedBudget
                                        )
                                        isLoading = false
                                        showSuccess = true
                                        performHaptic(context)
                                    } catch (e: Exception) {
                                        isLoading = false
                                        // Handle error if needed
                                    }
                                }
                            }
                        },
                        onBack = { step = 1 },
                        showSuccess = showSuccess,
                        isLoading = isLoading,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
private fun AddExpenseStepAmount(
    value: String,
    onInput: (String) -> Unit,
    onContinue: () -> Unit,
    showError: Boolean
) {
    val numberFormatter = remember {
        DecimalFormat("#,###", DecimalFormatSymbols(Locale.FRENCH).apply {
            groupingSeparator = ' '
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))

        // Step indicator
        Text(
            text = "Étape 1 sur 3 • Montant",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        // Amount display
        val displayAmount = if (value.isBlank()) "00" else {
            val numericValue = value.toDoubleOrNull()?.toInt() ?: 0
            numberFormatter.format(numericValue)
        }
        Text(
            text = "$displayAmount F",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        if (showError) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Montant invalide",
                color = Color.Red,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(80.dp))

        // Spacious keypad
        SpacedNumericKeypad(
            value = value,
            onValueChange = onInput
        )

        Spacer(Modifier.weight(1f))

        // Black continue button - not full width
        Button(
            onClick = onContinue,
            enabled = value.toDoubleOrNull() != null && value.toDouble() > 0.0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                disabledContainerColor = Color.Black.copy(alpha = 0.12f),
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.38f)
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .padding(bottom = 40.dp)
                .height(56.dp)
                .width(200.dp)
        ) {
            Text(
                text = "Continuer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SpacedNumericKeypad(
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val buttons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("00", "0", "⌫")
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                row.forEach { label ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE8E8E8))
                            .clickable {
                                when {
                                    label == "⌫" && value.isNotEmpty() -> {
                                        onValueChange(value.dropLast(1))
                                        performHaptic(context)
                                    }

                                    label == "00" -> {
                                        val newValue = if (value == "0") "0" else value + "00"
                                        if (newValue.toLongOrNull() != null && newValue.toLong() <= 999999999) {
                                            onValueChange(newValue)
                                            performHaptic(context)
                                        }
                                    }

                                    label in "0".."9" -> {
                                        val newValue = if (value == "0") label else value + label
                                        if (newValue.toLongOrNull() != null && newValue.toLong() <= 999999999) {
                                            onValueChange(newValue)
                                            performHaptic(context)
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (label == "⌫") {
                            Text(
                                text = "⌫",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        } else {
                            Text(
                                text = label,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddExpenseStepCategory(
    selected: ExpenseCategory?,
    onSelect: (ExpenseCategory) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    showError: Boolean
) {
    val bgColor = Color.White
    val fgColor = Color.Black
    val outlineColor = Color.Black.copy(alpha = 0.17f)
    // Local state for custom categories
    var customCats by remember { mutableStateOf(listOf<Pair<String, String>>()) } // Pair<emoji, label>
    var addDialogOpen by remember { mutableStateOf(false) }
    var newLabel by remember { mutableStateOf("") }
    var newEmoji by remember { mutableStateOf("") }
    val cats = remember(customCats) {
        listOf(
            Triple(ExpenseCategory.FOOD, "🍔", "ALIMENTATION"),
            Triple(ExpenseCategory.TRANSPORT, "🚗", "TRANSPORT"),
            Triple(ExpenseCategory.ENTERTAINMENT, "🎬", "LOISIRS"),
            Triple(ExpenseCategory.SHOPPING, "🛍", "SHOPPING"),
            Triple(ExpenseCategory.HEALTH, "💊", "SANTÉ"),
            Triple(ExpenseCategory.EDUCATION, "📚", "ÉDUCATION"),
            Triple(ExpenseCategory.BILLS, "💡", "FACTURE"),
            Triple(ExpenseCategory.OTHER, "🟢", "AUTRE")
        ) + customCats.map { Triple(null, it.first, it.second.uppercase()) }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header section
            Spacer(Modifier.height(38.dp))
            Text(
                text = "Étape 2 sur 3 • Catégorie",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = fgColor.copy(alpha = 0.6f),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 22.dp)
            )
            Spacer(Modifier.height(24.dp))
            // Flexible spacer to center the main content
            Spacer(Modifier.weight(0.3f))
            
            // Main content area
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Category selection area
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    items(cats.size) { idx ->
                        val (cat, emoji, lbl) = cats[idx]
                        val isSelected =
                            if (cat != null) cat == selected else selected == null && customCats.any { it.second.uppercase() == lbl }
                        val selectedBg = if (isSelected) fgColor else bgColor
                        val selectedFg = if (isSelected) bgColor else fgColor
                        
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    2.dp,
                                    if (isSelected) fgColor else outlineColor,
                                    RoundedCornerShape(16.dp)
                                )
                                .background(selectedBg)
                                .clickable {
                                    if (cat == null) onSelect(ExpenseCategory.OTHER) else onSelect(
                                        cat
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    emoji,
                                    fontSize = 24.sp,
                                    color = selectedFg
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    lbl,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = selectedFg,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                    
                    // "Add category" final item
                    item {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(2.dp, outlineColor, RoundedCornerShape(16.dp))
                                .background(bgColor)
                                .clickable { addDialogOpen = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text("+", fontSize = 24.sp, color = fgColor)
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "AJOUTER",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = fgColor,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                
                if (showError) {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "Sélectionne une catégorie",
                        color = errorRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
            
            // Flexible spacer to push buttons to bottom
            Spacer(Modifier.weight(0.4f))
            
            // Bottom buttons
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp), 
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onBack,
                    border = BorderStroke(0.dp, outlineColor),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = bgColor, contentColor = fgColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text("Retour", color = fgColor, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(18.dp))
                Button(
                    onClick = onContinue,
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fgColor,
                        contentColor = bgColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text("Continuer", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        if (addDialogOpen) {
            AlertDialog(
                onDismissRequest = { addDialogOpen = false },
                containerColor = bgColor,
                confirmButton = {
                    TextButton(onClick = {
                        if (newLabel.isNotBlank() && newEmoji.isNotBlank()) {
                            customCats = customCats + (newEmoji to newLabel.uppercase())
                            addDialogOpen = false
                            newLabel = ""
                            newEmoji = ""
                        }
                    }) { Text("Ajouter", color = fgColor, fontWeight = FontWeight.Bold) }
                },
                dismissButton = {
                    TextButton(onClick = { addDialogOpen = false }) {
                        Text(
                            "Annuler",
                            color = fgColor
                        )
                    }
                },
                title = { Text("Nouvelle catégorie", color = fgColor) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newLabel,
                            onValueChange = {
                                if (it.length <= 16) newLabel =
                                    it.filter { ch -> ch.isLetterOrDigit() || ch == ' ' }
                            },
                            placeholder = { Text("Nom") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp),
                            textStyle = TextStyle(color = fgColor)
                        )
                        Spacer(Modifier.height(13.dp))
                        OutlinedTextField(
                            value = newEmoji,
                            onValueChange = {
                                if (it.length < 3) newEmoji =
                                    it.filter { ch -> ch.isLetterOrDigit() || Character.getType(ch) == Character.OTHER_SYMBOL.toInt() }
                            },
                            singleLine = true,
                            placeholder = { Text("Emoji (e.g. 🍔)") },
                            modifier = Modifier.width(70.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 28.sp, color = fgColor),
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun AddExpenseStepInfos(
    date: Date,
    note: String,
    name: String,
    budgets: List<Budget>,
    selectedBudget: Budget?,
    onBudgetSelected: (Budget) -> Unit,
    onNoteChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onDateChange: (Date) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit,
    showSuccess: Boolean,
    isLoading: Boolean,
    context: Context
) {
    val bgColor = Color.White
    val fgColor = Color.Black
    val outlineColor = Color.Black.copy(alpha = 0.17f)
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.FRENCH) }
    var showCustomPicker by remember { mutableStateOf(false) }
    var pickerDate by remember { mutableStateOf(date) }
    var showError by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(38.dp))
            Text(
                text = "Étape 3 sur 3 • Informations",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = fgColor.copy(alpha = 0.6f),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 22.dp)
            )
            Spacer(Modifier.height(14.dp))
            Spacer(Modifier.height(28.dp))
            // Date pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF2F2F2))
                        .clickable { showCustomPicker = true }
                        .padding(vertical = 18.dp, horizontal = 18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.CalendarToday,
                            null,
                            tint = fgColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            dateFormat.format(date),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = fgColor
                        )
                    }
                }
            }
            if (showCustomPicker) {
                @OptIn(ExperimentalMaterial3Api::class)
                ModalBottomSheet(
                    onDismissRequest = { showCustomPicker = false },
                    containerColor = bgColor,
                    tonalElevation = 0.dp,
                    scrimColor = Color.Black.copy(alpha = 0.28f),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Choisir une date",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = fgColor
                        )
                        Spacer(Modifier.height(12.dp))
                        // Quick choose (aujourd'hui, hier) + picker
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = {
                                    pickerDate = Date()
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Aujourd'hui", color = fgColor) }
                            Spacer(Modifier.width(6.dp))
                            TextButton(
                                onClick = {
                                    val cal =
                                        Calendar.getInstance().apply { time = Date() }; cal.add(
                                        Calendar.DATE,
                                        -1
                                    ); pickerDate = cal.time
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Hier", color = fgColor) }
                            Spacer(Modifier.width(6.dp))
                            TextButton(
                                onClick = {
                                    val cal = Calendar.getInstance().apply { time = pickerDate }
                                    DatePickerDialog(
                                        context,
                                        { _, year, month, day ->
                                            val d = Calendar.getInstance()
                                                .apply { set(year, month, day) }.time
                                            pickerDate = d
                                        },
                                        cal.get(Calendar.YEAR),
                                        cal.get(Calendar.MONTH),
                                        cal.get(Calendar.DAY_OF_MONTH)
                                    ).show()
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Date...", color = fgColor) }
                        }
                        Spacer(Modifier.height(18.dp))
                        Text(
                            dateFormat.format(pickerDate),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 32.sp,
                            color = fgColor,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = {
                                showCustomPicker = false; onDateChange(pickerDate)
                            },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = fgColor,
                                contentColor = bgColor
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text("Valider", fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
            Spacer(Modifier.height(28.dp))
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = {
                    Text(
                        "Intitulé de la dépense",
                        color = fgColor.copy(alpha = 0.4f)
                    )
                },
                singleLine = true,
                isError = showError && name.isBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .background(bgColor),
                shape = RoundedCornerShape(0.dp),
                textStyle = TextStyle(color = fgColor, fontSize = 18.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = outlineColor,
                    unfocusedBorderColor = outlineColor
                )
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = note,
                onValueChange = onNoteChange,
                placeholder = {
                    Text(
                        "Ajouter une note (facultatif)",
                        color = fgColor.copy(alpha = 0.4f)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .background(bgColor),
                shape = RoundedCornerShape(0.dp),
                textStyle = TextStyle(color = fgColor, fontSize = 18.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = outlineColor,
                    unfocusedBorderColor = outlineColor
                )
            )
            Spacer(Modifier.height(10.dp))
            BudgetSelector(
                budgets = budgets,
                selectedBudget = selectedBudget,
                onBudgetSelected = onBudgetSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onBack,
                    border = BorderStroke(0.dp, outlineColor),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = bgColor, contentColor = fgColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text("Retour", color = fgColor, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(18.dp))
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onFinish()
                        } else {
                            showError = true
                        }
                    },
                    enabled = !isLoading,
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fgColor,
                        disabledContainerColor = fgColor.copy(alpha = 0.6f),
                        contentColor = bgColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = bgColor,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Ajouter", fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (showError && name.isBlank()) {
                Text(
                    text = "L'intitulé ne peut pas être vide",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
fun BudgetSelector(
    budgets: List<Budget>,
    selectedBudget: Budget?,
    onBudgetSelected: (Budget) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedBudget?.name ?: "Sélectionner un budget",
            onValueChange = { },
            readOnly = true,
            label = { Text("Budget") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dérouler")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            budgets.forEach { budget ->
                DropdownMenuItem(
                    text = { Text(budget.name) },
                    onClick = {
                        onBudgetSelected(budget)
                        expanded = false
                    }
                )
            }
        }
    }
}

@RequiresPermission(android.Manifest.permission.VIBRATE)
private fun performHaptic(context: Context) {
    val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        v.vibrate(VibrationEffect.createOneShot(90, VibrationEffect.EFFECT_TICK))
    } else {
        @Suppress("DEPRECATION")
        v.vibrate(35)
    }
}
