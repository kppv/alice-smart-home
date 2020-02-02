package ru.qrdp.home.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.qrdp.home.device.Home

@Service
class HomeService(private val home: Home) {

    @Scheduled(fixedDelay = 2000)
    fun doIt() {
        println(home.devices[0].name)
    }
}