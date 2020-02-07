package ru.qrdp.home.device

import com.fasterxml.jackson.annotation.JsonProperty

class State {
    var instance: String? = "on"
    var value: Boolean = false

    @get:JsonProperty("action_result")
    var actionResult: ActionResult = ActionResult()

    data class ActionResult(val status: String = "DONE")
}
