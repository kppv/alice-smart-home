package ru.qrdp.home.device

class Device {

    private val BRIGHTNESS = Pair("devices.capabilities.range", "brightness")
    private val CHANNEL = Pair("devices.capabilities.range", "channel")
    private val ENABLED = Pair("devices.capabilities.on_off", "on")
    private val SCALE = Pair("devices.capabilities.range", "temperature")
    private val SPEED = Pair("devices.capabilities.range", "volume")

    var id: String = "0"
    var name: String = ""
    var description: String = ""
    var room: String = ""
    var type: String = ""
    var capabilities: List<Capability> = emptyList()

    fun getCapability(capability: Capability): Capability? = getCapability(capability.type, capability.state.instance)

    private fun getCapability(type: String, instance: String): Capability? = capabilities
            .filter { it.type == type }
            .find { it.state.instance == instance }

    fun getBrightness() = getCapability(BRIGHTNESS.first, BRIGHTNESS.second)!!.state.value as Int

    fun getChannel() = getCapability(CHANNEL.first, CHANNEL.second)!!.state.value as Int

    fun getEnabled() = getCapability(ENABLED.first, ENABLED.second)!!.state.value as Boolean

    fun getSpeed() = getCapability(SPEED.first, SPEED.second)!!.state.value as Int

    fun getScale() = getCapability(SCALE.first, SCALE.second)!!.state.value as Int
}
