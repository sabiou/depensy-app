package xyz.godi.budgetmanager.data

import java.util.Date

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for Transaction.
 */

@Entity(tableName = "transaction")
data class Transaction (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        var id: Int = 0,
        var name: String,
        var amount: Float,
        var transactionDate: Date,
        val category: Category
)