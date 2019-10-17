package com.sn.quizapp.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.sn.quizapp.R
import com.sn.quizapp.ui.gameresult.ScoreListViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher


class RecyclerViewMatcher {


    fun withHolderTimeView(text: String): Matcher<RecyclerView.ViewHolder> {
        return object :
            BoundedMatcher<RecyclerView.ViewHolder, ScoreListViewHolder>(ScoreListViewHolder::class.java!!) {

            override fun describeTo(description: Description) {
                description.appendText("No ViewHolder found with text: $text")
            }

            override fun matchesSafely(item: ScoreListViewHolder): Boolean {
                val timeViewText =
                    item.itemView.findViewById(R.id.created_date) as TextView ?: return false
                return timeViewText.text.toString().contains(text)
            }
        }
    }

    fun recyclerItemAtPosition(position: Int, @NonNull itemMatcher: Matcher<View>): Matcher<View> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }
            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}