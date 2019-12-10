package dev.farouk.depensy.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Module used to define the connection between the framework's [ViewModelProvider.Factory] and
 * our own implementation: [DepensyViewModelFactory].
 */
@Module
@Suppress("UNUSED")
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: DepensyViewModelFactory):
            ViewModelProvider.Factory
}