package com.sn.quizapp.ui.gameresult


import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sn.quizapp.R
import com.sn.quizapp.ui.landingactivity.MainActivity
import com.sn.quizapp.ui.models.ScoreDetails
import com.sn.quizapp.utils.RecyclerViewMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ResultFragmentDataTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_fragment_showing_mocked_data() {

        configureData()

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withId(R.id.buttonPanel), 0), 3)
            )
        )

        materialButton.perform(scrollTo(), click())

        onView(withId(R.id.marksTxt)).check(matches(isDisplayed()))

        val stringVal = getString(R.string.current_score_is) + " 50"
        onView(withId(R.id.marksTxt)).check(matches(withText(stringVal)))

        onView(withId(R.id.btnQuit)).check(matches(isDisplayed()))

        onView(withId(R.id.btnRestart)).check(matches(isDisplayed()))

        onView(withId(R.id.btnRestart)).check(matches(isEnabled()))

        onView(withId(R.id.btnQuit)).check(matches(isEnabled()))

        onView(withId(R.id.scoreRecyclerView))
            .check(
                matches(
                    RecyclerViewMatcher().recyclerItemAtPosition(
                        0,
                        hasDescendant(withText("20"))
                    )
                )
            )

        onView(withId(R.id.scoreRecyclerView)).perform(
            RecyclerViewActions.scrollToHolder(
                RecyclerViewMatcher().withHolderTimeView("2019-10-18 10:35")
            )
        )

        onView(withId(R.id.scoreRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<ScoreListViewHolder>(26)
        )


        onView(withId(R.id.scoreRecyclerView)).perform(
            RecyclerViewActions.scrollToHolder(
                RecyclerViewMatcher().withHolderTimeView("2019-11-19 10:45")
            )
        )
    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    //Helper Methods-----------------

    //Access to String resources
    private fun getString(id: Int) = mActivityTestRule.activity.getString(id)

    private fun configureData() {

        mActivityTestRule.activity.mSharedVM.mCurrentScore.value = 50
        var listNew = ArrayList<ScoreDetails>()
        listNew.add(ScoreDetails(20, "2019-10-17 12:11"))
        listNew.add(ScoreDetails(50, "2019-10-16 10:15"))
        listNew.add(ScoreDetails(30, "2019-10-18 10:35"))
        listNew.add(ScoreDetails(40, "2019-10-20 10:45"))
        listNew.add(ScoreDetails(60, "2019-10-01 10:45"))
        listNew.add(ScoreDetails(70, "2019-10-19 10:15"))
        listNew.add(ScoreDetails(20, "2019-10-12 10:25"))
        listNew.add(ScoreDetails(50, "2019-10-11 10:45"))
        listNew.add(ScoreDetails(60, "2019-10-14 10:35"))
        listNew.add(ScoreDetails(10, "2019-10-16 10:15"))
        listNew.add(ScoreDetails(30, "2019-10-17 10:55"))
        listNew.add(ScoreDetails(50, "2019-10-12 10:15"))
        listNew.add(ScoreDetails(60, "2019-10-14 10:25"))
        listNew.add(ScoreDetails(70, "2019-10-12 10:60"))
        listNew.add(ScoreDetails(80, "2019-10-17 10:50"))
        listNew.add(ScoreDetails(90, "2019-10-19 10:45"))
        listNew.add(ScoreDetails(30, "2019-10-17 10:35"))
        listNew.add(ScoreDetails(50, "2019-10-13 10:55"))
        listNew.add(ScoreDetails(90, "2019-10-12 10:15"))
        listNew.add(ScoreDetails(10, "2019-10-11 10:25"))
        listNew.add(ScoreDetails(20, "2019-10-14 10:45"))
        listNew.add(ScoreDetails(60, "2019-10-16 10:25"))
        listNew.add(ScoreDetails(70, "2019-10-17 10:35"))
        listNew.add(ScoreDetails(80, "2019-10-13 10:55"))
        listNew.add(ScoreDetails(30, "2019-10-19 10:25"))
        listNew.add(ScoreDetails(40, "2019-10-11 10:35"))
        listNew.add(ScoreDetails(70, "2019-11-19 10:45"))

        mActivityTestRule.activity.mSharedVM.mListOfScores.value = listNew

    }
}
