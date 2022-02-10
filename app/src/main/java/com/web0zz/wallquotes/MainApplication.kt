package com.web0zz.wallquotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        if(BuildConfig.DEBUG) {
            Timber.plant(
                object: Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String {
                        return "(${element.fileName}:${element.lineNumber}:${Thread.currentThread().name})#${element.methodName}"
                    }
                }
            )
        }
    }
}
