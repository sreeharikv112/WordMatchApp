package com.sn.quizapp.ui.models

data class UIUpdationModel(

    val correctAnswer: Boolean,
    val question : String,
    val answer : String,
    val totalScore: Int,
    val questionCount : Int
)