package com.hsccc.myspringbootstarter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeatController {
    @GetMapping("/heartbeat")
    public boolean HeartBeat() {
        return true;
    }
}
