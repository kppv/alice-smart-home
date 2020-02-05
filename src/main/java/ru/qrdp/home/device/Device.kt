package ru.qrdp.home.device

class Device {
    var id: Int = 0
    var name: String = ""
    var type: String = ""
    var capabilities: List<Capability> = emptyList()
}
