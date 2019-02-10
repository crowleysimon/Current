package com.crowleysimon.current.injection

import android.app.Application
import com.crowleysimon.current.CurrentApp
import com.crowleysimon.current.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        PresentationModule::class,
        DataModule::class,
        CacheModule::class,
        RemoteModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: CurrentApp)
}