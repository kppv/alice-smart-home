package ru.qrdp.home.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.qrdp.home.device.Device
import ru.qrdp.home.device.Home
import ru.qrdp.home.integration.BacklightIntegrationService
import ru.qrdp.home.integration.LampIntegrationService
import ru.qrdp.home.service.HomeService
import javax.annotation.PostConstruct

@Service
class HomeServiceImpl(
    private val mapper: ObjectMapper,
    private val lampIntegrationService: LampIntegrationService,
    private val backlightIntegrationService: BacklightIntegrationService
) :
    HomeService {

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

        var capability = existedDevice.getCapabilityByState(device.capabilities[0])

        if (capability == null) {
            capability = existedDevice.getCapabilityByType(device.capabilities[0])!!
            capability.state.instance = device.capabilities[0].state.instance
            capability.state.value = device.capabilities[0].state.value
        } else {
            capability.state.value = device.capabilities[0].state.value
        }

        when (existedDevice.id) {
            "lamp-02" -> lampIntegrationService.changeState(existedDevice)
            "backlight-02" -> backlightIntegrationService.changeState(existedDevice)
        }
    }
}
