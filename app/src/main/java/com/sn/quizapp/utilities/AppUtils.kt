package com.sn.quizapp.utilities

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


/**
 * Utility methods can be added into this.
 * Can be used throughout app for multiple helpers.
 */
class AppUtils(var context: Context) {

    val TAG = AppUtils::class.java.simpleName


    fun getCurrentDateTimeFormatted() : String{
        val todayDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(todayDate)
    }
}