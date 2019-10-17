package com.sn.quizapp.app

import android.app.Application
import com.sn.quizapp.di.appUtils
import com.sn.quizapp.di.fileUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyQuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    fun configureDi() = startKoin {
        androidContext(applicationContext)
        modules(provideComponent())

    }

    fun provideComponent() = listOf(appUtils,fileUtils)
}