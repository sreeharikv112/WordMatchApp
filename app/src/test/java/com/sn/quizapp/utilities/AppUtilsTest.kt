package com.sn.quizapp.utilities

import android.app.Application
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito


@RunWith(JUnit4::class)
class AppUtilsTest {

    val mockedAndroidContext = Mockito.mock(Application::class.java)

    lateinit var mAppUtils : AppUtils

    @Before
    fun setUp() {
        mAppUtils = AppUtils(mockedAndroidContext)
    }

    @Test
    fun test_current_date_time_formated_returns_valid_data(){

        val data = mAppUtils.getCurrentDateTimeFormatted()
        assertNotNull(data)

    }

    //Helper Methods-----------------

}