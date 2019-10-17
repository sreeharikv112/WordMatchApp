package com.sn.quizapp.ui.models

/**
 * Model for holding different params to calculate user input and score
 */
data class UIUpdationModel(

    val correctAnswer: Boolean,
    val question : String,
    val answer : String,
    val totalScore: Int,
    val questionCount : Int
)