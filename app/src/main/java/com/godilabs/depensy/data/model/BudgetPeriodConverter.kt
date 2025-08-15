package com.godilabs.depensy.data.model

import androidx.room.TypeConverter

class BudgetPeriodConverter {
    @TypeConverter
    fun toBudgetPeriod(value: String?) = value?.let { enumValueOf<BudgetPeriod>(it) }

    @TypeConverter
    fun fromBudgetPeriod(value: BudgetPeriod?) = value?.name
}