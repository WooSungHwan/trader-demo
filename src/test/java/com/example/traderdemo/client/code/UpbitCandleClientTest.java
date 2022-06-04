package com.example.traderdemo.client.code;

import com.example.traderdemo.app.common.enums.MarketType;
import com.example.traderdemo.upbit.UpbitCandleClient;
import com.trader.common.utils.MinuteCandle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UpbitCandleClientTest {

    @Autowired
    UpbitCandleClient upbitCandleClient;

    @Test
    void test() {
        List<MinuteCandle> minuteCandles =
                upbitCandleClient.getMinuteCandles(5, MarketType.KRW_BTC, 200, LocalDateTime.now());
        System.out.println(minuteCandles);
        assertThat(minuteCandles.size()).isEqualTo(200);
    }

}
