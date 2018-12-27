package xyz.godi.budgetmanager.data

import androidx.room.*

/**
 * Data class for Categories
 */
@Entity(tableName = "category",
        foreignKeys = [ForeignKey(entity = Budget::class, parentColumns = ["id"], childColumns = ["budgetId"])],
        indices = [Index("budgetId")])

data class Category (
        @ColumnInfo(name = "budgetId") val budgetId: String,

        @ColumnInfo(name = "name")
        val name: String,

        private val description: String
) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var categoryId: String = ""
}