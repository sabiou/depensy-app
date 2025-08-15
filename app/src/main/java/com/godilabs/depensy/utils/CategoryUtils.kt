package com.godilabs.depensy.utils

import androidx.compose.ui.graphics.Color
import com.godilabs.depensy.data.model.ExpenseCategory

object CategoryUtils {
    fun getCategoryEmoji(category: ExpenseCategory): String {
        return when (category) {
            ExpenseCategory.FOOD -> "🍔"
            ExpenseCategory.TRANSPORT -> "🚗"
            ExpenseCategory.ENTERTAINMENT -> "🎬"
            ExpenseCategory.SHOPPING -> "🛍"
            ExpenseCategory.HEALTH -> "💊"
            ExpenseCategory.EDUCATION -> "📚"
            ExpenseCategory.BILLS -> "💡"
            ExpenseCategory.OTHER -> "🟢"
        }
    }

    fun getCategoryLabel(category: ExpenseCategory): String {
        return when (category) {
            ExpenseCategory.FOOD -> "Alimentation"
            ExpenseCategory.TRANSPORT -> "Transport"
            ExpenseCategory.ENTERTAINMENT -> "Loisirs"
            ExpenseCategory.SHOPPING -> "Shopping"
            ExpenseCategory.HEALTH -> "Santé"
            ExpenseCategory.EDUCATION -> "Éducation"
            ExpenseCategory.BILLS -> "Facture"
            ExpenseCategory.OTHER -> "Autre"
        }
    }

    fun getCategoryColor(category: ExpenseCategory): Color {
        return when (category) {
            ExpenseCategory.FOOD -> Color(0xFFFF6B6B)
            ExpenseCategory.TRANSPORT -> Color(0xFF4ECDC4)
            ExpenseCategory.ENTERTAINMENT -> Color(0xFFFFE66D)
            ExpenseCategory.SHOPPING -> Color(0xFFFF8B94)
            ExpenseCategory.HEALTH -> Color(0xFF95E1D3)
            ExpenseCategory.EDUCATION -> Color(0xFFA8E6CF)
            ExpenseCategory.BILLS -> Color(0xFFFCE38A)
            ExpenseCategory.OTHER -> Color(0xFFD4C5F9)
        }
    }
}