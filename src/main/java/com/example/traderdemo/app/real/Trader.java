package com.example.traderdemo.app.real;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Trader {

    public void run() {
        System.out.println("Trader.run");
    }

    @Scheduled(fixedDelay = 1000)
    public void test() {
        System.out.println(1);
    }

}
