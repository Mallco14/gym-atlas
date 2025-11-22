package com.upc.gym_atlas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/api/test")
    public String testApi() {
        logger.info("ENDPOINT /api/test → API funcionando correctamente");
        return "API funcionando correctamente";
    }
}
