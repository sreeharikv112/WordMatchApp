package com.sn.quizapp.di

import com.sn.quizapp.utilities.FileReaderUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Koin module for file reader utility class.
 */
val fileUtils = module {

    single { FileReaderUtils(androidApplication()) }

}