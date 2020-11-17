package dev.farouk.depensy.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.farouk.depensy.data.db.ExpenseDatabase
import javax.inject.Singleton

/**
 * Created by Farouk on 17/11/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
class PersistanceModule {

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application) = Room.databaseBuilder(
        application, ExpenseDatabase::class.java, "expenses"
    ).build()

    @Provides
    @Singleton
    fun providesExpensesDao(db: ExpenseDatabase) = db.expenseDao()

    @Provides
    @Singleton
    fun providesCategoryDao(db: ExpenseDatabase) = db.categoryDao()
}