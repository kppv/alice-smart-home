package ru.qrdp.home.integration.impl

import org.springframework.stereotype.Service
import ru.qrdp.home.common.getLogger
import ru.qrdp.home.device.Device
import ru.qrdp.home.integration.LampIntegrationService
import java.net.URL

@Service
class LampIntegrationServiceImpl : LampIntegrationService {
    private val log = getLogger(this::class.java)

    override fun changeState(device: Device) {
        val url = "http://192.168.0.120/" +
                "?mode=${device.getChannel()}" +
                "&speed=${device.getSpeed()}" +
                "&scale=${device.getScale()}" +
                "&brightness=${device.getBrightness() * 2}" +
                "&on=${device.getEnabled()}"
        log.info("Send request: {}", url)
        URL(url).readBytes()
    }
}