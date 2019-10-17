package com.sn.quizapp.ui.gameresult


import android.graphics.Color

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sn.quizapp.R
import com.sn.quizapp.base.archcomponents.SharedVM
import com.sn.quizapp.base.uihelpers.BaseFragment
import com.sn.quizapp.base.uihelpers.GenericAdapter
import com.sn.quizapp.ui.landing.MainFragment
import com.sn.quizapp.ui.landingactivity.MainActivity
import com.sn.quizapp.ui.models.ScoreDetails
import com.sn.quizapp.utilities.AppLogger
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_result.btnQuit as button_quit
import kotlinx.android.synthetic.main.fragment_result.btnRestart as button_restart
import kotlinx.android.synthetic.main.fragment_result.marksTxt as txt_marks
import kotlinx.android.synthetic.main.fragment_result.scoreRecyclerView as recyclerview
import kotlinx.android.synthetic.main.fragment_result.viewKonfetti as konfettiView


/**
 * Fragment for score result details view.
 * Populates views with data from Shared View Model.
 * Navigates user click to game screen or exists app.
 *
 */
class ResultFragment : BaseFragment(), View.OnClickListener {

    companion object {

        fun getFragment() = ResultFragment()

    }
    //Shared view model
    lateinit var mViewModel: SharedVM
    //Recycler adapter for user list of scores
    lateinit var mRecyclerAdapter: GenericAdapter<ScoreDetails>
    //App logger
    val mAppLog: AppLogger by inject()
    //Logger tag
    val TAG = ResultFragment::class.java.simpleName


    override fun getLayoutId() = R.layout.fragment_result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedVM::class.java]
        }!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_restart.setOnClickListener(this)
        button_quit.setOnClickListener(this)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        mRecyclerAdapter = object : GenericAdapter<ScoreDetails>(this) {
            override fun getLayoutId(position: Int, obj: ScoreDetails): Int {
                return R.layout.scores_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ScoreListViewHolder(view)
            }
        }
        recyclerview.adapter = mRecyclerAdapter

        txt_marks.text = getString(R.string.current_score_is) + " ${mViewModel.mCurrentScore.value}"
        var listItems: ArrayList<ScoreDetails>
        if (null == mViewModel.mListOfScores.value) {
            listItems = arrayListOf()
        } else {
            listItems = mViewModel.mListOfScores.value as ArrayList<ScoreDetails>
        }
        mRecyclerAdapter.setItems(listItems)
        activity!!.title = getString(R.string.score_details)

        try {
            konfettiView.build()
                .addColors(
                    resources.getColor(R.color.ColorOne),
                    resources.getColor(R.color.ColorTwo),
                    resources.getColor(R.color.ColorThree))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(1000L)// Impacts performance
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12))
                .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)
        } catch (e: Exception) {
            mAppLog.logE(TAG, "Exception in konfettiView = $e")
        }
    }

    override fun onClick(v: View?) {
        val landingActivity = activity as MainActivity
        if (v!!.id == R.id.btnRestart) {
            landingActivity.replaceFragment(MainFragment.getFragment())
        } else if (v!!.id == R.id.btnQuit) {
            landingActivity.exitApp()
        }
    }
}
