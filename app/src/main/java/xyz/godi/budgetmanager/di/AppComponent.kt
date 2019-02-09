package xyz.godi.budgetmanager.di

import android.app.Application
import androidx.core.view.KeyEventDispatcher
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun Build(): AppComponent
    }
}