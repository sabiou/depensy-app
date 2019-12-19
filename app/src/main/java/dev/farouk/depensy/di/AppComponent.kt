package dev.farouk.depensy.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.farouk.depensy.DepensyApplication
import dev.farouk.depensy.core.di.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivityBindingModule::class,
            ViewModelModule::class]
)

interface AppComponent: AndroidInjector<DepensyApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DepensyApplication): AppComponent
    }
}