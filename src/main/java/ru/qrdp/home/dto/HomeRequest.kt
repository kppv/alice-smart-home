package ru.qrdp.home.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class HomeRequest(
        @get:JsonProperty("payload")
        val payload: Payload
)
