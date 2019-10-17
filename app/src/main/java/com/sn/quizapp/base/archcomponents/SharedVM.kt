package com.sn.quizapp.base.archcomponents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sn.quizapp.ui.models.ScoreDetails

class SharedVM: ViewModel() {

    var mCurrentScore = MutableLiveData(0)

    var mListOfScores = MutableLiveData<ArrayList<ScoreDetails>>()

}