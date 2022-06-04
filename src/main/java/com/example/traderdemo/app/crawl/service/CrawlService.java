package com.example.traderdemo.app.crawl.service;

import com.example.traderdemo.app.common.enums.MarketType;
import com.trader.common.enums.MinuteType;

import java.time.LocalDateTime;

public interface CrawlService {
    void collectGetCoinCandles(MinuteType minute, MarketType market, LocalDateTime targetDate) throws Exception ;
}
