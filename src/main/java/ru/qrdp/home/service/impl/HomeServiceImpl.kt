package ru.qrdp.home.service.impl

import org.springframework.stereotype.Service
import ru.qrdp.home.device.Home
import ru.qrdp.home.service.HomeService

@Service
class HomeServiceImpl(private val home: Home) : HomeService {
    override fun getHome(): Home = home
}
