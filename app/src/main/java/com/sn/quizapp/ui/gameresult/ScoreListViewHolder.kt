package com.sn.quizapp.ui.gameresult

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sn.quizapp.R
import com.sn.quizapp.base.uihelpers.GenericAdapter
import com.sn.quizapp.ui.models.ScoreDetails

/**
 * View holder class for users score recycler view.
 */
class ScoreListViewHolder : RecyclerView.ViewHolder, GenericAdapter.Binder<ScoreDetails> {

    var scoreView: TextView
    var dateView: TextView

    constructor(itemView: View) : super(itemView) {
        scoreView = itemView.findViewById(R.id.score_value)
        dateView = itemView.findViewById(R.id.created_date)
    }

    override fun bind(data: ScoreDetails) {
        scoreView.text = data.score.toString()
        dateView.text = data.date
    }
}