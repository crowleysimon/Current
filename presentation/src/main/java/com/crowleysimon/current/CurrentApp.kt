package com.crowleysimon.current

import android.app.Activity
import android.app.Application
import com.crowleysimon.current.injection.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject

class CurrentApp : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        configureDagger()
        configureLeakCanary()
        JodaTimeAndroid.init(this)
    }

    private fun configureLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun configureDagger() {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }
}