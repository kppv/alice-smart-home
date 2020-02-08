package ru.qrdp.home.device

class Device {
    var id: String = "0"
    var name: String = ""
    var description: String = ""
    var room: String = ""
    var type: String = ""
    var capabilities: List<Capability> = emptyList()

    fun getCapability(type: String): Capability? = capabilities.find { it.type.equals(type) }
}
