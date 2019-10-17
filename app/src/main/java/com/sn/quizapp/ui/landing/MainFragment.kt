package com.sn.quizapp.ui.landing


import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sn.quizapp.R
import com.sn.quizapp.base.archcomponents.SharedVM
import com.sn.quizapp.base.archcomponents.ViewModelFactoryBase
import com.sn.quizapp.base.uihelpers.BaseFragment
import com.sn.quizapp.ui.gameresult.ResultFragment
import com.sn.quizapp.ui.landingactivity.MainActivity
import com.sn.quizapp.ui.models.ScoreDetails
import com.sn.quizapp.ui.models.UIUpdationModel
import com.sn.quizapp.utilities.AppLogger
import com.sn.quizapp.utilities.AppUtils
import com.sn.quizapp.utilities.FileReaderUtils
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_main.answerTxt as txt_answer
import kotlinx.android.synthetic.main.fragment_main.btnCorrect as button_correct
import kotlinx.android.synthetic.main.fragment_main.btnWrong as button_wrong
import kotlinx.android.synthetic.main.fragment_main.progress_bar_horizontal as progress_bar
import kotlinx.android.synthetic.main.fragment_main.questionTxt as txt_question
import kotlinx.android.synthetic.main.fragment_main.question_count as txt_current_question_count
import kotlinx.android.synthetic.main.fragment_main.total_score as txt_total_score


/**
 * Landing fragment view.
 * Shows alert dialog for user with info.
 * upon clicking shows multiple questions with possible answers animated.
 * Navigates to detail screen once completed.
 */
class MainFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun getFragment() = MainFragment()
    }

    //Inject apputils
    val mAppUtils: AppUtils by inject()
    //Inject file utils
    val mFileUtils: FileReaderUtils by inject()
    //Logger
    val mLogger: AppLogger by inject()
    //Target View model
    lateinit var mViewModel: MainFragmentVM
    //Common VM
    lateinit var mCommonViewModel: SharedVM
    //Tag required for logging
    val TAG = MainFragment::class.java.simpleName
    //correct answer to verify
    var mCorrectAnswer = false
    //record user click first time
    var mUserDidClickedOption = false


    //layout id for fragment
    override fun getLayoutId() = R.layout.fragment_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val array = arrayOf(mAppUtils, mFileUtils)
        this.mViewModel =
            ViewModelProviders.of(this, ViewModelFactoryBase(array)).get(MainFragmentVM::class.java)
        this.mCommonViewModel = activity?.run {
            ViewModelProviders.of(this)[SharedVM::class.java]
        }!!
        this.mViewModel.mUIModelUpdated.observeForever(this.updateUIModelObserver)
        this.mViewModel.mShouldNavigateToResultScreen.observeForever(this.moveToResultScreenObserver)

        button_correct.setOnClickListener(this)
        button_wrong.setOnClickListener(this)

        showAlertDialog()
        activity!!.title = getString(R.string.match_words)
    }

    //Start showing question answer animations
    private fun animateUI() {
        val animation1 = AnimationUtils.loadAnimation(activity, R.anim.move_linear)
        txt_answer.startAnimation(animation1)
        animation1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                requestNextQuestion()
            }
        })
    }

    //get next set of questions from VM
    fun requestNextQuestion() {
        this.mViewModel.populateDataForUser()
    }

    //Observer for updated set of answer data
    private val updateUIModelObserver = Observer<UIUpdationModel> {

        try {
            mUserDidClickedOption = false
            mCorrectAnswer = it.correctAnswer
            txt_answer.text = it.answer
            txt_question.text = it.question
            txt_total_score.text = "Score : ${it.totalScore} "
            txt_current_question_count.text = "[ ${it.questionCount} / 10 ]"
            progress_bar.progress = it.questionCount
            animateUI()
        } catch (e: Exception) {
            mLogger.logE(TAG, "exception in updateUIModelObserver = $e")
        }
    }

    //Observer for completion of questions
    private val moveToResultScreenObserver = Observer<Boolean> {
        if (it == true) {
            var listItem = this.mCommonViewModel.mListOfScores.value

            val scoreDetails = ScoreDetails(
                this.mCommonViewModel.mCurrentScore.value!!,
                mAppUtils.getCurrentDateTimeFormatted()
            )
            if (listItem == null)
                listItem = ArrayList<ScoreDetails>()
            listItem.add(scoreDetails)
            this.mCommonViewModel.mListOfScores.value = listItem

            this.mViewModel.resetAllValues()
            val baseActivity = activity as MainActivity?
            baseActivity?.replaceFragment(ResultFragment.getFragment())
        }
    }

    //Increase score , for correct answer
    private fun incrementScore() {
        this.mViewModel.mTotalScore.value = this.mViewModel.mTotalScore.value!! + 10
        this.mCommonViewModel.mCurrentScore.value = this.mViewModel.mTotalScore.value
        txt_total_score.text =
            getString(R.string.score) + " " + "${this.mViewModel.mTotalScore.value}"
    }

    //Show alert dialog
    private fun showAlertDialog() {
        showAlert(
            R.string.press_to_start_game, android.R.string.ok, R.string.quit_app,
            DialogInterface.OnClickListener { dialog, which ->
                this.mViewModel.populateDataForUser()
            },
            DialogInterface.OnClickListener { dialog, which ->
                val landingActivity = activity as MainActivity
                landingActivity.exitApp()
            })
    }

    //Handle click events
    override fun onClick(v: View?) {
        if (v!!.id == R.id.btnCorrect) {
            if (!mUserDidClickedOption) {
                mUserDidClickedOption = true
                if (mCorrectAnswer) {
                    incrementScore()
                }
                requestNextQuestion()
            }
        } else if (v!!.id == R.id.btnWrong) {
            if (!mUserDidClickedOption) {
                mUserDidClickedOption = true
                if (!mCorrectAnswer) {
                    incrementScore()
                }
                requestNextQuestion()
            }
        }
    }
}
