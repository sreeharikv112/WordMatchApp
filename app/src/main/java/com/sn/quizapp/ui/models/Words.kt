package com.sn.quizapp.ui.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Words(
    @JsonProperty("text_eng")
    val text_eng : String,

    @JsonProperty("text_spa")
    val text_spa : String
)