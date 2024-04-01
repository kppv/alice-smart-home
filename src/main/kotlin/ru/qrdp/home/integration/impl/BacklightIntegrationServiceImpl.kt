package ru.qrdp.home.integration.impl

import org.springframework.stereotype.Service
import ru.qrdp.home.common.getLogger
import ru.qrdp.home.device.Device
import ru.qrdp.home.integration.BacklightIntegrationService
import ru.qrdp.home.integration.LampIntegrationService
import java.net.URL

@Service
class BacklightIntegrationServiceImpl : BacklightIntegrationService {
    private val log = getLogger(this::class.java)

    private val scenes = mapOf(
        "candle" to 22,
        "fantasy" to 25,
        "garland" to 14,
        "jungle" to 29,
        "neon" to 19,
        "night" to 8,
        "party" to 14,
        "rest" to 11,
        "siren" to 26,
        "sunrise" to 30,
        "sunset" to 3
    )

    override fun changeState(device: Device) {

        var url = "http://192.168.1.86/?brightness=${device.getBrightness() * 2}&on=${device.getEnabled()}"

        device.getRGB()?.let {
            val decimalColor = device.getRGB()!!
            val red = (decimalColor shr 16) and 0xFF
            val green = (decimalColor shr 8) and 0xFF
            val blue = decimalColor and 0xFF

            url += "&mode=777&r=${red}&g=${green}&b=${blue}"
        }

        device.getScene()?.let {
            url += "&mode=${scenes[device.getScene()!!]}"
        }

        log.info("Send request: {}", url)
        URL(url).readBytes()
    }
}