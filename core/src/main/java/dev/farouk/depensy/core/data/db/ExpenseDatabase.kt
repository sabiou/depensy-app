package dev.farouk.depensy.core.data.db

import android.content.Context
import androidx.room.*

/**
 * A database that stores Expense information.
 */
@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = true)

@TypeConverters(LocalDateConverter::class)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun expenseDao(): ExpenseFtsDao

    companion object {

        private const val databaseName = "expenses-db"

        fun buildDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(
                    context, ExpenseDatabase::class.java, databaseName
            )
                    // since there is no migration yet we'll use fallbackToDestructiveMigration() for now.
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}