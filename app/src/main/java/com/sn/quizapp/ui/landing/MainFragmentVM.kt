package com.sn.quizapp.ui.landing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sn.quizapp.R
import com.sn.quizapp.ui.models.UIUpdationModel
import com.sn.quizapp.ui.models.Words
import com.sn.quizapp.utilities.AppUtils
import com.sn.quizapp.utilities.FileReaderUtils
import kotlin.random.Random

/**
 * View model for main fragment BL.
 * Generates questions and answers by reading local file content.
 * Calculates user scores.
 * Intimate UI for updation.
 */
class MainFragmentVM(appUtils: AppUtils, fileReaderUtils: FileReaderUtils) : ViewModel() {

    //Data for presenting different UI elements to user
    lateinit var mCorrectAnswer: MutableLiveData<Boolean>
    lateinit var mQuestion: MutableLiveData<String>
    lateinit var mAnswer: MutableLiveData<String>
    lateinit var mTotalScore: MutableLiveData<Int>
    lateinit var mQuestionCount: MutableLiveData<Int>
    lateinit var mUIModelUpdated: MutableLiveData<UIUpdationModel>
    lateinit var mShouldNavigateToResultScreen: MutableLiveData<Boolean>

    //Total questions count
    var mTotalQuestionsCount = 0
    //Random num
    lateinit var mRandomNumber: MutableLiveData<Int>
    //Random Delta
    lateinit var mRandomDelta: MutableLiveData<Int>
    //Data collection
    lateinit var mWordsCollection: Array<Words>
    //Tag for logging
    val TAG = MainFragmentVM::class.java.simpleName

    init {
        resetAllValues()
        mTotalQuestionsCount = 10
        val fileContent = fileReaderUtils.readFileContent("input_data.json")
        mWordsCollection = fileReaderUtils.convertStringToJSON(fileContent)
        mQuestionCount.value = 0
    }

    //Calculate random question answer combinations for user
    fun populateDataForUser() {

        //Increase the question count
        if (mQuestionCount.value!! < 10) {
            mQuestionCount.value = mQuestionCount.value!! + 1

            mRandomNumber.value = generateRandom(0, mWordsCollection.size - 1)
            mRandomDelta.value = generateRandom(1, mWordsCollection.size - 2)

            mQuestion.value = mWordsCollection[mRandomNumber.value!!].text_eng

            //If random delta is factor of 2, then put correct answer as option to user
            //Or else put wrong answer
            if (mRandomDelta.value!! % 2 == 0) {
                mAnswer.value = mWordsCollection[mRandomNumber.value!!].text_spa
                mCorrectAnswer.value = true
            } else {
                if (mRandomNumber.value!! != mRandomDelta.value!!)
                    mAnswer.value = mWordsCollection[mRandomDelta.value!!].text_spa
                else
                    mAnswer.value =
                        mWordsCollection[Random.nextInt(1, mWordsCollection.size - 2)].text_spa
                mCorrectAnswer.value = false

            }
            updateUI()
        } else {
            navigateToResultScreen()
            resetAllValues()
        }
    }

    //get random number from a provided range
    fun generateRandom(from: Int, toRange: Int) =
        Random.nextInt(from, toRange)

    //Signal for navigation to result screen
    fun navigateToResultScreen() {
        mShouldNavigateToResultScreen.value = true
    }

    //reset all view model values
    fun resetAllValues() {
        mCorrectAnswer = MutableLiveData(false)
        mQuestion = MutableLiveData("")
        mAnswer = MutableLiveData("")
        mTotalScore = MutableLiveData(0)
        mQuestionCount = MutableLiveData(0)
        mUIModelUpdated = MutableLiveData()
        mShouldNavigateToResultScreen = MutableLiveData(false)
        mTotalQuestionsCount = 0
        mRandomNumber = MutableLiveData(0)
        mRandomDelta = MutableLiveData(0)
    }

    //Create data model for updating UI
    fun updateUI(): UIUpdationModel {
        val updatedUIModel = UIUpdationModel(
            mCorrectAnswer.value!!,
            mQuestion.value!!,
            mAnswer.value!!,
            mTotalScore.value!!,
            mQuestionCount.value!!
        )
        mUIModelUpdated.postValue(updatedUIModel)
        return updatedUIModel
    }
}