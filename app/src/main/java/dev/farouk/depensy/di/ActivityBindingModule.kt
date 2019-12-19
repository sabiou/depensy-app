package dev.farouk.depensy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.farouk.depensy.core.di.ActivityScoped
import dev.farouk.depensy.ui.MainActivity
import dev.farouk.depensy.ui.MainActivityModule
import dev.farouk.depensy.ui.info.InfoModule

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                MainActivityModule::class,
                InfoModule::class])
    internal abstract fun mainActivity(): MainActivity
}