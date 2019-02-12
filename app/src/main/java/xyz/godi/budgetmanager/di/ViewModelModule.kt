package xyz.godi.budgetmanager.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import xyz.godi.budgetmanager.info.ui.InfoViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    abstract fun bindInfoViewModel(infoViewModel: InfoViewModel): ViewModel

}