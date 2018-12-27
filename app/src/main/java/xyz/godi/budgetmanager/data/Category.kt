package xyz.godi.budgetmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Data class for Categories
 */
@Entity(tableName = "category")
data class Category (
        @ColumnInfo(name = "name")
        val name: String,
        private val description: String
)