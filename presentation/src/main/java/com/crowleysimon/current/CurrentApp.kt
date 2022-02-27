package com.crowleysimon.current

import android.app.Application
import com.crowleysimon.current.injection.cacheModule
import com.crowleysimon.current.injection.dataModule
import com.crowleysimon.current.injection.presentationModule
import com.crowleysimon.current.injection.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CurrentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configureKoin()
        configureTimber()
        //registerActivityLifecycleCallbacks(CustomTabsActivityLifecycleCallbacks())
    }

    private fun configureTimber() {
        if (BuildConfig.BUILD_TYPE == "debug") {
            Timber.plant(Timber.DebugTree())
        } else {
            //Timber.plant(ReleaseTree())
        }
    }

    private fun configureKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CurrentApp)
            modules(cacheModule, dataModule, presentationModule, remoteModule)
        }
    }
}