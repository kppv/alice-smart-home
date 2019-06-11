package ru.smart.home;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Controller {

    private Map<String, Boolean> STATE = new HashMap<>();

    {
        STATE.put("L1", false);
        STATE.put("L2", false);
    }


    @Value("classpath:devices.json")
    private Resource devices;

    @Value("classpath:lamp.json")
    private Resource lamp;


    /**
     * Ping от платформы Умный.Дом
     *
     * @param body тело запроса для логгирования
     * @return 200 OK
     */
    @PostMapping("/")
    public ResponseEntity ping(@RequestBody String body) {
        log.info("Ping {}", body);
        return ResponseEntity.ok().build();
    }

    /**
     * Список устройств для платформы Умный.Дом
     *
     * @param request нужен для извлечения хедеров
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/v1.0/user/devices", produces = "application/json")
    public ResponseEntity<String> devices(HttpServletRequest request) throws IOException {
        String response = getDevices(request);
        log.info(response);
        return ResponseEntity.ok(response);
    }

    /**
     * Состояние устройства для платформы Умный.Дом
     *
     * @param request нужен для извлечения хедеров
     * @param body    тело запроса
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/v1.0/user/devices/query", produces = "application/json")
    public ResponseEntity<String> state(HttpServletRequest request, @RequestBody String body) throws IOException {
        String id = body.substring(body.indexOf("\"id\"") + 6, body.indexOf("\"id\"") + 8); //не заморачивался с моедлями, просто костыльно вытащил id
        log.info("ID: {}", id);
        String response = getDeviceState(request, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Умный.Дом меняет состояние устройства
     *
     * @param request нужен для извлечения хедеров
     * @param body    тело запроса
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/v1.0/user/devices/action", produces = "application/json")
    public ResponseEntity<String> setState(HttpServletRequest request, @RequestBody String body) throws IOException {
        String id = body.substring(body.indexOf("\"id\"") + 6, body.indexOf("\"id\"") + 8); //не заморачивался с моедлями, просто костыльно вытащил id
        String state = body.substring(body.indexOf("value") + 7, body.indexOf("value") + 11); //не заморачивался с моедлями, просто костыльно вытащил состояние
        STATE.put(id, Boolean.valueOf(state));
        log.info("ID: {}, State: {}", id, state);
        String response = getDeviceState(request, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Вывод состояния для отладки
     *
     * @return
     */
    @GetMapping(value = "/state")
    public ResponseEntity getSate() {
        return ResponseEntity.ok(STATE);
    }

    private String getDevices(HttpServletRequest request) throws IOException {
        String rid = request.getHeader("X-Request-Id");
        String token = request.getHeader("Authorization");
        log.info("Request: {}, Token: {}", rid, token);
        return FileUtils.readFileToString(devices.getFile(), "UTF-8")
                .replace("{rid}", rid);
    }

    private String getDeviceState(HttpServletRequest request, String id) throws IOException {
        String rid = request.getHeader("X-Request-Id");
        String token = request.getHeader("Authorization");
        log.info("Request: {}, Token: {}", rid, token);
        return FileUtils.readFileToString(lamp.getFile(), "UTF-8")
                .replace("{rid}", rid)
                .replace("{id}", id)
                .replace("\"{state}\"", String.valueOf(STATE.get(id)));
    }
}
