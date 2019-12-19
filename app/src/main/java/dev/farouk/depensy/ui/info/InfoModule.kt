package dev.farouk.depensy.ui.info

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.farouk.depensy.core.di.FragmentScoped
import dev.farouk.depensy.core.di.ViewModelKey

@Module
@Suppress("UNUSED")
internal abstract class InfoModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [InfoFragmentsModule::class])
    internal abstract fun contributeInfoFragment(): InfoFragment

    @Binds
    @ViewModelKey(InfoViewModel::class)
    abstract fun bindInfoViewModel(viewModel: InfoViewModel): ViewModel
}