package dev.farouk.depensy.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "category_")
data class Category (
        @ColumnInfo
        val title: String,
        @ColumnInfo
        val color: Int
)