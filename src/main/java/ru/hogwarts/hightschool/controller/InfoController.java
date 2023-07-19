package ru.hogwarts.hightschool.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.hightschool.service.InfoService;

import java.util.stream.Stream;

@RestController
public class InfoController {

    private final int port;
    private final InfoService infoService;

    public InfoController(@Value("${server.port}") int port, InfoService infoService) {
        this.port = port;
        this.infoService = infoService;
    }


    @GetMapping("/getPort")
    public int getPort() {
        return port;
    }

//    Создать эндпоинт (не важно в каком контроллере), который будет возвращать целочисленное значение.
    @GetMapping("/fast")
    public int getFast() {
        return infoService.getFast();
    }
}
