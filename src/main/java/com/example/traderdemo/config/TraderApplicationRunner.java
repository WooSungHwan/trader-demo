package com.example.traderdemo.config;

import com.example.traderdemo.app.backtest.BackTester;
import com.example.traderdemo.app.real.Trader;
import com.example.traderdemo.app.crawl.Crawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TraderApplicationRunner implements ApplicationRunner {
    private final ApplicationMode applicationMode;

    // TODO 애매하다. 나누기.
    private final Trader trader;
    private final BackTester backTester;
    private final Crawler crawler;

    @Override
    public void run(ApplicationArguments args) {
        try {
            switch (applicationMode.getApplicationMode()) {
                case BACK_TEST -> backTester.run();
                case REAL_TRADE -> { return; }
                case CRAWL -> crawler.run();
                default -> throw new RuntimeException();
            }
        } catch (Exception e) {
            log.error("[트레이더 에러] application.yml 에서 application.mode 속성을 확인해주세요. 현재값 : {}", applicationMode.getMode());
        }
    }
}
