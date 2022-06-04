package com.example.traderdemo.config;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class TraderExitCodeGenerator implements ExitCodeGenerator {

    @Override
    public int getExitCode() {
        return 42;
    }

}