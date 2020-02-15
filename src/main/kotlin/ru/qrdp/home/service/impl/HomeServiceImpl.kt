package ru.qrdp.home.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.qrdp.home.device.Device
import ru.qrdp.home.device.Home
import ru.qrdp.home.integration.LampIntegrationService
import ru.qrdp.home.service.HomeService
import javax.annotation.PostConstruct

@Service
class HomeServiceImpl(private val mapper: ObjectMapper, private val lampIntegrationService: LampIntegrationService) : HomeService {

    @Value("classpath:devices.json")
    private lateinit var devicesFile: Resource

    private lateinit var devices: Map<String, Device>
    private lateinit var home: Home

    @PostConstruct
    private fun init() {
        home = mapper.readValue<Home>(devicesFile.inputStream, Home::class.java)
        devices = home.devices.map { it.id to it }.toMap()
    }

    override fun getHome(): Home = home

    override fun getDevice(id: String): Device = devices[id] ?: error("Устройство с id[$id] не найдено")

    override fun changeState(device: Device) {
        val existedDevice = getDevice(device.id)
        existedDevice.getCapability(device.capabilities[0])?.let { it.state.value = device.capabilities[0].state.value }
        lampIntegrationService.changeState(existedDevice)
    }
}
