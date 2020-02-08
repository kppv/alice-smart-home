package ru.qrdp.home.service.impl

import org.springframework.stereotype.Service
import ru.qrdp.home.device.Device
import ru.qrdp.home.device.Home
import ru.qrdp.home.device.State
import ru.qrdp.home.service.HomeService
import javax.annotation.PostConstruct

@Service
class HomeServiceImpl(private val home: Home) : HomeService {
    private lateinit var devices: Map<String, Device>

    @PostConstruct
    private fun init() {
        devices = home.devices.map { it.id to it }.toMap()
    }

    override fun getHome(): Home = home

    override fun getDevice(id: String): Device? = devices[id]

    override fun changeState(device: Device) {
        getDevice(device.id)?.getCapability(device.capabilities[0].type)
                ?.also { it.state.value = device.capabilities[0].state.value }
    }
}
