package ru.qrdp.home.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import ru.qrdp.home.device.Device

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Payload(
        @get:JsonProperty("user_id")
        val userId: String? = null,

        @get:JsonProperty("devices")
        val devices: List<Device>
)
