package ru.qrdp.home.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.qrdp.home.device.Home
import ru.qrdp.home.service.HomeService

@RestController
@RequestMapping("/api")
class HomeController(
        private val homeService: HomeService
) {

    /**
     * Вывод всего Дома
     *
     * @return Устройства Дома
     */
    @GetMapping(value = ["/home"])
    fun getState(): ResponseEntity<Home> = ResponseEntity.ok(homeService.getHome())
}
