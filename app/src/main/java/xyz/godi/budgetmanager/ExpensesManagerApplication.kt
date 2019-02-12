package xyz.godi.budgetmanager

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import xyz.godi.budgetmanager.di.AppComponent
import xyz.godi.budgetmanager.di.DaggerAppComponent
import javax.inject.Inject

class ExpensesManagerApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}