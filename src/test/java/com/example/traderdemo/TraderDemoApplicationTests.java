package com.example.traderdemo;

import com.example.traderdemo.app.crawl.service.CrawlService;
import com.example.traderdemo.app.common.enums.MarketType;
import com.example.traderdemo.upbit.UpbitCandleClient;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TraderDemoApplicationTests {

    @Autowired
    CrawlService crawlService;

    @Autowired
    UpbitCandleClient upbitCandleClient;

    @Test
    void contextLoads() throws Exception {

        List<MinuteCandle> minuteCandles = upbitCandleClient.getMinuteCandles(5, MarketType.KRW_BTC, 200, LocalDateTime.now());
        assertThat(minuteCandles.size()).isEqualTo(200);



















        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        log.info("======= 최근일자 수집 시작 =====");
        for (MarketType marketType : MarketType.marketTypeList) {
            log.info("======= {} 시작 =====",marketType.getName());
            오늘부터_특정일자까지_수집(MinuteType.FIVE, marketType, LocalDateTime.of(2022, 1, 1, 0, 0, 0));
            log.info("======= {} 종료 =====", marketType.getName());
        };
        log.info("======= 최근일자 수집 종료 =====");
    }

    private void 오늘부터_특정일자까지_수집(MinuteType minuteType, MarketType marketType, LocalDateTime targetDate) throws Exception {
        crawlService.collectGetCoinCandles(minuteType, marketType, targetDate);
    }

}
