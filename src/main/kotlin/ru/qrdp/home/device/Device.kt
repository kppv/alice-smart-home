package ru.qrdp.home.device

import com.fasterxml.jackson.annotation.JsonIgnore

class Device {

    private val BRIGHTNESS = Pair("devices.capabilities.range", "brightness")
    private val CHANNEL = Pair("devices.capabilities.range", "channel")
    private val ENABLED = Pair("devices.capabilities.on_off", "on")
    private val SCALE = Pair("devices.capabilities.range", "temperature")
    private val SPEED = Pair("devices.capabilities.range", "volume")
    private val RGB = Pair("devices.capabilities.color_setting", "rgb")
    private val SCENE = Pair("devices.capabilities.color_setting", "scene")

    var id: String = "0"
    var name: String = ""
    var description: String = ""
    var room: String = ""
    var type: String = ""
    var capabilities: List<Capability> = emptyList()

    fun getCapabilityByType(capability: Capability): Capability? = capabilities.find { it.type == capability.type }

    fun getCapabilityByState(capability: Capability): Capability? =
        getCapabilityByState(capability.type, capability.state.instance)

    private fun getCapabilityByState(type: String, instance: String): Capability? = capabilities
        .filter { it.type == type }
        .find { it.state.instance == instance }

    @JsonIgnore
    fun getBrightness() = getCapabilityByState(BRIGHTNESS.first, BRIGHTNESS.second)!!.state.value as Int

    @JsonIgnore
    fun getChannel() = getCapabilityByState(CHANNEL.first, CHANNEL.second)!!.state.value as Int

    @JsonIgnore
    fun getEnabled() = getCapabilityByState(ENABLED.first, ENABLED.second)!!.state.value as Boolean

    @JsonIgnore
    fun getSpeed() = getCapabilityByState(SPEED.first, SPEED.second)!!.state.value as Int

    @JsonIgnore
    fun getScale() = getCapabilityByState(SCALE.first, SCALE.second)!!.state.value as Int

    @JsonIgnore
    fun getRGB() = getCapabilityByState(RGB.first, RGB.second)?.state?.value as Int?

    @JsonIgnore
    fun getScene() = getCapabilityByState(SCENE.first, SCENE.second)?.state?.value as String?
}
