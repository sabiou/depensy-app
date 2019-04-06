package dev.farouk.depensy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores Expense information.
 * And a global method to get access to the database.
 */
@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {
    /**
     * Connect our database to the DAO
     */
    abstract fun expenseDao(): ExpenseDao

    /**
     * Define a companion object, this allows us to add functions on the ExpenseDatabase class.
     *
     * For example, clients can call `ExpenseDatabase.getInstance(context)` to instantiate
     * a new ExpenseDatabase
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): ExpenseDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // if instance is `null` make a new databse instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            ExpenseDatabase::class.java,
                            "expenses_story_database"
                    )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build()
                    // assign INSTANCE to new created database
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}