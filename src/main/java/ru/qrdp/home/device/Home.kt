package ru.qrdp.home.device

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "home")
class Home {
    var devices: List<Device> = emptyList()
}