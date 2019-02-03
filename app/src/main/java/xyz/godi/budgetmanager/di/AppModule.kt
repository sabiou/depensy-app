package xyz.godi.budgetmanager.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import xyz.godi.budgetmanager.db.ExpensesDb
import xyz.godi.expenses.db.DailyExpenseDao
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule {

     @Provides
     @Singleton
     fun provideContext(application: Application): Context {
         return application.applicationContext
     }

    // provides Room database object
    @Provides
    @Singleton
    fun provideDataBase(context: Context): ExpensesDb {
        return Room.databaseBuilder(context, ExpensesDb::class.java, ExpensesDb.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providesDailyExpensesDao(database: ExpensesDb): DailyExpenseDao {
        return database.dailyExpenseDao()
    }

    @Provides
    @Singleton
    fun providesExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }
}