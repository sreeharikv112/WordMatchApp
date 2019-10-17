package com.sn.quizapp.ui.gameresult

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.sn.quizapp.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ResultFragmentTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
    }

    @Test
    fun test_view_items_populated(){

        launchFragmentInContainer<ResultFragment>(
            themeResId = R.style.AppTheme)

        onView(withId(R.id.btnRestart)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.btnQuit)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.btnRestart)).check(matches(ViewMatchers.isEnabled()))

        onView(withId(R.id.btnQuit)).check(matches(ViewMatchers.isEnabled()))
    }


}