package com.sn.quizapp.di

import com.sn.quizapp.utilities.FileReaderUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val fileUtils = module {

    single { FileReaderUtils(androidApplication()) }

}