package cn.alone.reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * Created by zhangrenjie on 2018-12-04
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(ServerRequest request) {
        return "hello";
    }

}
