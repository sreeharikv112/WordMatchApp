package com.sn.quizapp.ui.landing

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.sn.quizapp.ui.models.Words
import com.sn.quizapp.utilities.AppUtils
import com.sn.quizapp.utilities.FileReaderUtils
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainFragmentVMTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel : MainFragmentVM

    @Mock
    lateinit var appUtils: AppUtils

    @Mock
    lateinit var fileReaderUtils: FileReaderUtils

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainFragmentVM(appUtils,fileReaderUtils)
    }

    @Test
    fun test_populate_data_for_user_with_delta_factor_even(){

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)
        pre_populate_data_for_user(spiedViewModel)
        doReturn(3).`when`(spiedViewModel).generateRandom(com.nhaarman.mockitokotlin2.any(),com.nhaarman.mockitokotlin2.any())
        spiedViewModel.populateDataForUser()
        verify(spiedViewModel, Mockito.times(1)).updateUI()
    }

    @Test
    fun test_all_vm_values_are_defaulted(){
        mainViewModel.resetAllValues()
        assertEquals(false,mainViewModel.mCorrectAnswer.value)
        assertEquals("",mainViewModel.mQuestion.value)
        assertEquals("",mainViewModel.mAnswer.value)
        assertEquals(0,mainViewModel.mTotalScore.value)
        assertEquals(0,mainViewModel.mQuestionCount.value)
        assertEquals( null,mainViewModel.mUIModelUpdated.value)
        assertEquals(false,mainViewModel.mShouldNavigateToResultScreen.value)
        assertEquals(0,mainViewModel.mTotalQuestionsCount)
        assertEquals(0,mainViewModel.mRandomNumber.value)
        assertEquals(0,mainViewModel.mRandomDelta.value)
    }

    @Test
    fun test_populate_data_for_user_with_delta_factor_odd(){

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)
        pre_populate_data_for_user(spiedViewModel)
        doReturn(2).`when`(spiedViewModel).generateRandom(com.nhaarman.mockitokotlin2.any(),com.nhaarman.mockitokotlin2.any())
        spiedViewModel.populateDataForUser()
        verify(spiedViewModel, Mockito.times(1)).updateUI()
    }

    @Test
    fun test_navigate_to_next_from_populate_data_method(){

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)
        pre_populate_data_for_user(spiedViewModel)
        spiedViewModel.mQuestionCount.value = 30
        spiedViewModel.populateDataForUser()
        verify(spiedViewModel, Mockito.times(1)).navigateToResultScreen()
    }

    @Test
    fun test_resetvalues_called_from_populate_data_method(){

        val spiedViewModel = com.nhaarman.mockitokotlin2.spy(this.mainViewModel)
        pre_populate_data_for_user(spiedViewModel)
        spiedViewModel.mQuestionCount.value = 30
        spiedViewModel.populateDataForUser()
        verify(spiedViewModel, Mockito.times(1)).resetAllValues()
    }

    @Test
    fun test_update_ui_populating_new_vm(){

        populate_default_values_for_newvm()
        val populatedResponse = mainViewModel.updateUI()
        assertEquals("Answer",populatedResponse.answer)
        assertEquals(true,populatedResponse.correctAnswer)
        assertEquals("Question",populatedResponse.question)
        assertEquals(20,populatedResponse.totalScore)
        assertEquals(3,populatedResponse.questionCount)
    }

    //Helpers-----
    private fun populate_default_values_for_newvm() {
        mainViewModel.mCorrectAnswer.value = true
        mainViewModel.mQuestion.value = "Question"
        mainViewModel.mAnswer.value = "Answer"
        mainViewModel.mTotalScore.value = 20
        mainViewModel.mQuestionCount.value = 3
    }

    private fun pre_populate_data_for_user(spiedViewModel: MainFragmentVM) {
        val stringContent = "[\r\n  {\r\n    \"text_eng\": \"primary school\",\r\n    \"text_spa\": \"escuela primaria\"\r\n  },\r\n  {\r\n    \"text_eng\": \"teacher\",\r\n    \"text_spa\": \"profesor  profesora\"\r\n  },\r\n  {\r\n    \"text_eng\": \"pupil\",\r\n    \"text_spa\": \"alumno alumna\"\r\n  },\r\n  {\r\n    \"text_eng\": \"holidays\",\r\n    \"text_spa\": \"vacaciones \"\r\n  },\r\n  {\r\n    \"text_eng\": \"class\",\r\n    \"text_spa\": \"curso\"\r\n  },\r\n  {\r\n    \"text_eng\": \"bell\",\r\n    \"text_spa\": \"timbre\"\r\n  },\r\n  {\r\n    \"text_eng\": \"group\",\r\n    \"text_spa\": \"grupo\"\r\n  },\r\n  {\r\n    \"text_eng\": \"exercise book\",\r\n    \"text_spa\": \"cuaderno\"\r\n  },\r\n  {\r\n    \"text_eng\": \"quiet\",\r\n    \"text_spa\": \"quieto\"\r\n  }]"
        val gson = Gson()
        spiedViewModel.mWordsCollection = gson.fromJson(stringContent, Array<Words>::class.java)
    }

}