package ru.qrdp.home.controller

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import ru.qrdp.home.common.getLogger
import ru.qrdp.home.common.toHomeHeaders
import ru.qrdp.home.common.toJson
import ru.qrdp.home.device.Home
import ru.qrdp.home.dto.HomeRequest
import ru.qrdp.home.dto.HomeResponse
import ru.qrdp.home.dto.Payload
import ru.qrdp.home.service.HomeService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/")
class MainController {

    @GetMapping(value = ["/"], produces = ["application/json"])
    fun getState(): ResponseEntity<String> = ok("Hello")

}
