package xyz.godi.budgetmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "budget")
data class Budget (
        @PrimaryKey @ColumnInfo(name = "id") val budgetId : String,
        var name: String,
        var amount: Float,
        var category: Category,
        var startDate: Date,
        var endDate: Date
)