package dev.farouk.depensy.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.farouk.depensy.data.Converters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "expenses_table")
@TypeConverters(Converters::class)
@Parcelize
data class Expense(
        @PrimaryKey(autoGenerate = true)
        var expenseId: Int,

        @ColumnInfo(name = "expense_value")
        val value: Double,

        @ColumnInfo(name = "expense_description")
        val description: String,

        @ColumnInfo(name = "expense_date")
        val date: Date?
): Parcelable