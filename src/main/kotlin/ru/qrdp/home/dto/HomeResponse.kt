package ru.qrdp.home.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class HomeResponse(
        @get:JsonProperty("request_id")
        val requestId: String?,

        @get:JsonProperty("payload")
        val payload: Payload
)
