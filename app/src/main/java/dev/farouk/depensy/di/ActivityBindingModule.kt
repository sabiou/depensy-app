package dev.farouk.depensy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.farouk.depensy.core.di.ActivityScoped

@Module
@Suppress("UNUSED")
annotation class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector()
}