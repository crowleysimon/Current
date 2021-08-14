package com.crowleysimon.current

import android.app.Application
import com.crowleysimon.current.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject



class CurrentApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        configureDagger()
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

    private fun configureDagger() {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}