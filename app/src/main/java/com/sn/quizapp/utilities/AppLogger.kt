package com.sn.quizapp.utilities

import android.util.Log

class AppLogger {

    fun logD(tag: String, message: String){
        Log.d(tag,message)
    }

    fun logE(tag: String, message: String){
        Log.e(tag,message)
    }

    fun logV(tag: String, message: String){
        Log.v(tag,message)
    }

    fun logI(tag: String, message: String){
        Log.i(tag,message)
    }
}