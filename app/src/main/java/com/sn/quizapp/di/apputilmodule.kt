package com.sn.quizapp.di

import com.sn.quizapp.utilities.AppLogger
import com.sn.quizapp.utilities.AppUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appUtils = module {

    single { AppUtils(androidApplication()) }
    single { AppLogger() }

}