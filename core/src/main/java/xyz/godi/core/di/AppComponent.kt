package xyz.godi.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
class AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun Build(): AppComponent
    }
}