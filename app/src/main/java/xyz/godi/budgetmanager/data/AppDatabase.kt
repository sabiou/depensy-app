package xyz.godi.budgetmanager.data

import android.content.Context
import androidx.room.*
import xyz.godi.budgetmanager.utils.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(entities = [Budget::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun budgetDao(): BudgetDAO
    abstract fun transaction(): TransactionDao

    // For Singleton instantiation
    @Volatile private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance?: synchronized(this) {
            instance?: createDatabase(context).also {instance = it}
        }
    }

    private fun createDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}