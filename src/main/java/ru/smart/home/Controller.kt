package ru.smart.home

import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URL
import javax.servlet.http.HttpServletRequest

@RestController
class Controller {

    @Value("classpath:devices.json")
    private val devices: Resource? = null

    @Value("classpath:lamp.json")
    private val lamp: Resource? = null

    private val log: Logger = LoggerFactory.getLogger(Controller::class.java)

    private val states = mutableMapOf("L1" to false, "L2" to false)

    /**
     * Вывод состояния для отладки
     *
     * @return
     */
    @GetMapping(value = ["/state"])
    fun getState(r: HttpServletRequest): ResponseEntity<Map<String, Boolean>> {
        log.info("Get State from {}, [{}]", r.getHeader("X-FORWARDED-FOR"), r.getHeader("User-Agent"))
        return ResponseEntity.ok(states)
    }

    /**
     * Ping от платформы Умный.Дом
     *
     * @param body тело запроса для логгирования
     * @return 200 OK
     */
    @PostMapping("/v1.0")
    fun ping(@RequestBody body: String): ResponseEntity<*> {
        log.info("Ping {}", body)
        return ResponseEntity.ok().build<Any>()
    }

    /**
     * Список устройств для платформы Умный.Дом
     *
     * @param request нужен для извлечения хедеров
     * @return
     */
    @GetMapping(value = ["/v1.0/user/devices"], produces = ["application/json"])
    fun devices(request: HttpServletRequest): ResponseEntity<String> {
        if (request.getHeader("X-Request-Id") == null) return ResponseEntity.ok("ok")
        val response = getDevices(request)
        log.info(response)
        return ResponseEntity.ok(response)
    }

    /**
     * Состояние устройства для платформы Умный.Дом
     *
     * @param request нужен для извлечения хедеров
     * @param body    тело запроса
     * @return
     */
    @PostMapping(value = ["/v1.0/user/devices/query"], produces = ["application/json"])
    fun state(request: HttpServletRequest, @RequestBody body: String): ResponseEntity<String> {
        val id = body.substring(body.indexOf("\"id\"") + 6, body.indexOf("\"id\"") + 8) //не заморачивался с моедлями, просто костыльно вытащил id
        log.info("ID: {}", id)
        val response = getDeviceState(request, id)
        return ResponseEntity.ok(response)
    }

    /**
     * Умный.Дом меняет состояние устройства
     *
     * @param request нужен для извлечения хедеров
     * @param body    тело запроса
     * @return
     */
    @PostMapping(value = ["/v1.0/user/devices/action"], produces = ["application/json"])
    fun setState(request: HttpServletRequest, @RequestBody body: String): ResponseEntity<String> {
        val id = body.substring(body.indexOf("\"id\"") + 6, body.indexOf("\"id\"") + 8) //не заморачивался с моедлями, просто костыльно вытащил id
        val state = body.substring(body.indexOf("value") + 7, body.indexOf("value") + 11) //не заморачивался с моедлями, просто костыльно вытащил состояние
        states[id] = state.toBoolean()

        val toggle = if (state.toBoolean()) "on" else "off"

        URL("http://192.168.0.120:8088/$toggle").readBytes()

        log.info("ID: {}, State: {}", id, state)
        val response = getDeviceState(request, id)
        return ResponseEntity.ok(response)
    }

    @PostMapping(value = ["/v1.0/user/unlink"], produces = ["application/json"])
    fun unlink(request: HttpServletRequest, @RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    private fun getDevices(request: HttpServletRequest): String {
        val rid = request.getHeader("X-Request-Id")
        val token = request.getHeader("Authorization")
        log.info("Request: {}, Token: {}", rid, token)
        return FileUtils.readFileToString(devices!!.file, "UTF-8")
                .replace("{rid}", rid)
    }

    private fun getDeviceState(request: HttpServletRequest, id: String): String {
        val rid = request.getHeader("X-Request-Id")
        val token = request.getHeader("Authorization")
        log.info("Request: {}, Token: {}", rid, token)
        return FileUtils.readFileToString(lamp!!.file, "UTF-8")
                .replace("{rid}", rid)
                .replace("{id}", id)
                .replace("\"{state}\"", states[id].toString())
    }
}
