package ru.qrdp.home.integration

import ru.qrdp.home.device.Device

interface BacklightIntegrationService {
    fun changeState(device: Device)
}