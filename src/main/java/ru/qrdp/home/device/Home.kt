package ru.qrdp.home.device

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "home")
class Home {
    var devices: List<Device> = emptyList()
}
