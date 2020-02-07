package ru.qrdp.home.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.qrdp.home.device.Device

class Payload(
        @get:JsonProperty("user_id")
        val userId: String? = "1",

        @get:JsonProperty("devices")
        val devices: List<Device>?
)
