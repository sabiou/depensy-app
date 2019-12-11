package dev.farouk.depensy.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dev.farouk.depensy.DepensyApplication
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AppModule::class]
)

interface AppComponent: AndroidInjector<DepensyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DepensyApplication): AppComponent
    }
}