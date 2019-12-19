package dev.farouk.depensy.ui.info

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.farouk.depensy.core.di.ChildFragmentScoped

@Module
internal abstract class InfoFragmentsModule {
    /**
     * Generates an [AndroidInjector] for the [FaqFragment].
     */
    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFaqFragment(): FaqFragment
}