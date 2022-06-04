package com.example.traderdemo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationExiter {

    private final ApplicationContext applicationContext;
    private final ExitCodeGenerator exitCodeGenerator;

    public void exit() {
        log.info("[트레이더 종료] 시스템을 종료합니다.");
        SpringApplication.exit(applicationContext, exitCodeGenerator);
    }
}
