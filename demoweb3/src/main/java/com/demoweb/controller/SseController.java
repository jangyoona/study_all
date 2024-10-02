package com.demoweb.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@RestController
public class SseController {

    // 스케쥴러 개념 = 정보를 끊지 않고 요청을 계속 받음 Flux. => /home 에 받는 자바스크립트 코드 있음
    @GetMapping(path = { "/stream-sse" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvent() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return Flux.interval(Duration.ofSeconds(3)) // 3초마다 한번씩 이 로직을 수행해라.
                .map(sequence -> "SSE event - " + sdf.format(new Date()));
    }
}
