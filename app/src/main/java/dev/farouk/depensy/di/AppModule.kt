package dev.farouk.depensy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.farouk.depensy.DepensyApplication
import dev.farouk.depensy.core.data.db.ExpenseDatabase

@Module
class AppModule  {

    @Provides
    fun provideContext(application: DepensyApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun providesAppDatabase(context: Context): ExpenseDatabase = ExpenseDatabase.buildDatabase(context)

}