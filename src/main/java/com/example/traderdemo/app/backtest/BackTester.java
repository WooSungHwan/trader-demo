package com.example.traderdemo.app.backtest;

import com.example.traderdemo.app.common.annotation.Exit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BackTester {

    @Exit
    public void run() {

    }

}
