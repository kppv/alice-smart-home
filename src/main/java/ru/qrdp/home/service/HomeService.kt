package ru.qrdp.home.service

import ru.qrdp.home.device.Device
import ru.qrdp.home.device.Home
import ru.qrdp.home.device.State

interface HomeService {
    fun getHome(): Home

    fun getDevice(id: String): Device?

    fun changeState(device: Device)
}
