package com.example.traderdemo.app.crawl;

import com.example.traderdemo.app.common.annotation.Exit;
import com.example.traderdemo.app.common.enums.MarketType;
import com.example.traderdemo.app.crawl.service.CrawlService;
import com.trader.common.enums.MinuteType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class Crawler {

    private final CrawlService crawlService;

    @Exit
    public void run() throws Exception {
        crawlService.collectGetCoinCandles(MinuteType.FIVE, MarketType.KRW_BTC, LocalDateTime.of(2022, 1, 1, 0, 0, 0));
    }

}
