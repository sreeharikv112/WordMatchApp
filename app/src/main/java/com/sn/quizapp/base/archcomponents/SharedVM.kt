package com.sn.quizapp.base.archcomponents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sn.quizapp.ui.models.ScoreDetails

/**
 * Shared view model class.
 * This can be used with multiple fragments.
 */
class SharedVM: ViewModel() {

    var mCurrentScore = MutableLiveData(0)

    var mListOfScores = MutableLiveData<ArrayList<ScoreDetails>>()

}