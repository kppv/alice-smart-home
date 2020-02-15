package ru.qrdp.home.integration

import ru.qrdp.home.device.Device

interface LampIntegrationService {
    fun changeState(device: Device)
}