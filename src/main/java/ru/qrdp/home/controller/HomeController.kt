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
@RequestMapping("/api")
class HomeController(
        private val homeService: HomeService
) {
    private val log = getLogger(this::class.java)

    /**
     * Вывод всего Дома
     *
     * @return Устройства Дома
     */
    @GetMapping(value = ["/home"], produces = ["application/json"])
    fun getState(): ResponseEntity<Home> = ok(homeService.getHome())


    /**
     * Ping от платформы Умный.Дом
     *
     * @return 200 OK
     */
    @RequestMapping(value = ["/v1.0"], method = [RequestMethod.HEAD], produces = ["application/json"])
    fun ping(): ResponseEntity<*> = log.info("Ping").let { ok().build<Any>() }

    /**
     * Список устройств для платформы Умный.Дом
     *
     * @param request для извлечения хедеров
     * @return
     */
    @GetMapping(value = ["/v1.0/user/devices"], produces = ["application/json"])
    fun devices(request: HttpServletRequest): ResponseEntity<HomeResponse> =
            HomeResponse(request.toHomeHeaders().requestId, Payload(devices = homeService.getHome().devices))
                    .also { log.info("Devices: {}", it.toJson()) }
                    .let { ok(it) }

    /**
     * Состояние устройства для платформы Умный.Дом
     *
     * @param request для извлечения хедеров
     * @return
     */
    @PostMapping(value = ["/v1.0/user/devices/query"], produces = ["application/json"])
    fun query(request: HttpServletRequest): ResponseEntity<HomeResponse> =
            HomeResponse(request.toHomeHeaders().requestId, Payload(devices = homeService.getHome().devices))
                    .also { log.info("Query: {}", it.toJson()) }
                    .let { ok(it) }

    /**
     * Умный.Дом меняет состояние устройства
     *
     * @param request для извлечения хедеров
     * @return
     */
    @PostMapping(value = ["/v1.0/user/devices/action"], produces = ["application/json"])
    fun action(@RequestBody homeRequest: HomeRequest, request: HttpServletRequest): ResponseEntity<HomeResponse> =
            homeService.changeState(homeRequest.payload.devices!![0])
                    .run { HomeResponse(request.toHomeHeaders().requestId, Payload(devices = homeService.getHome().devices)) }
                    .also { log.info("Action: {}", it.toJson()) }
                    .let { ok(it) }


    /**
     * Оповещает о разъединении аккаунта провайдера и аккаунта на Яндексе.
     * Если пользователь разъединил аккаунты, пользовательский токен отзывается независимо от корректности
     * полученного ответа на запрос.
     */
    @PostMapping(value = ["/v1.0/user/unlink"], produces = ["application/json"])
    fun unlink(): ResponseEntity<String> = ok("ok")

}
