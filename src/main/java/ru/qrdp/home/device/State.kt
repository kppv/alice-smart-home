package ru.qrdp.home.device

import com.fasterxml.jackson.annotation.JsonProperty

class State {
    var instance: String = ""
    var value: Any? = null

    @get:JsonProperty("action_result")
    var actionResult: ActionResult = ActionResult()

    data class ActionResult(val status: String = "DONE")
}
