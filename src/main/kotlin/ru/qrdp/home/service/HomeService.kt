package ru.qrdp.home.service

import ru.qrdp.home.device.Device
import ru.qrdp.home.device.Home

interface HomeService {
    fun getHome(): Home

    fun getDevice(id: String): Device?

    fun changeState(device: Device)
}
