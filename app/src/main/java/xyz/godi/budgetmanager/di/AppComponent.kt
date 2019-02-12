package xyz.godi.budgetmanager.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import xyz.godi.budgetmanager.ExpensesManagerApplication

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(expensesManagerApplication: ExpensesManagerApplication)
}