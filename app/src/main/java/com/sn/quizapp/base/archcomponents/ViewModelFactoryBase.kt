package com.sn.quizapp.base.archcomponents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sn.quizapp.ui.landing.MainFragmentVM
import com.sn.quizapp.utilities.AppUtils
import com.sn.quizapp.utilities.FileReaderUtils

/**
 * Factory class for view models
 */
class ViewModelFactoryBase (val mParams: Array<Any>?) :
    ViewModel(), ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        when (modelClass) {
            MainFragmentVM::class.java -> {

                val appUtils = mParams?.get(0) as AppUtils
                val fileUtils = mParams?.get(1) as FileReaderUtils

                return MainFragmentVM(appUtils,fileUtils) as T
            }

            SharedVM::class.java -> {
                return SharedVM() as T
            }

            else -> throw kotlin.ClassCastException()
        }
    }
}
