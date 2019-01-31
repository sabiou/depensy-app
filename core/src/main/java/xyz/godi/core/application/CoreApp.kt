package xyz.godi.core.application

import android.app.Application

open class CoreApp : Application() {
    // Implementation to come

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        // TO be implemented
    }
}