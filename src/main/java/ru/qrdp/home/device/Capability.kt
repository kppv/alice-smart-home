package ru.qrdp.home.device

class Capability {
    var type: String = ""
    var retrievable: Boolean = true
    var parameters: Map<String, Any> = emptyMap()
    var state: State = State()
}
