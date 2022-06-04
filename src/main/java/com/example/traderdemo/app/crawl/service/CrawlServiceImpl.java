package com.example.traderdemo.app.crawl.service;

import com.example.traderdemo.app.common.repository.FiveMinutesCandleRepository;
import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import com.example.traderdemo.app.common.enums.MarketType;
import com.example.traderdemo.upbit.UpbitCandleClient;
import com.trader.common.enums.MinuteType;
import com.trader.common.utils.MinuteCandle;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlServiceImpl implements CrawlService {

    private final FiveMinutesCandleRepository fiveMinutesCandleRepository;
    private final UpbitCandleClient upbitCandleClient;

//    @Transactional
    @Override
    public void collectGetCoinCandles(MinuteType minuteType, MarketType market, @NonNull LocalDateTime targetDate) throws Exception {
        int minute = minuteType.getMinute();
        LocalDateTime nextTo = LocalDateTime.now().minusMinutes(minute); // 현재 만들어지는 분봉에 의해서 값이 왜곡된다. 현재 진행중인 분봉의 직전봉부터 가져온다.

        boolean flag = true;
        while (flag) {
            int size = 0;
            long start = System.currentTimeMillis();
            List<MinuteCandle> minuteCandles = upbitCandleClient.getMinuteCandles(minute, market, 200, nextTo);

            for (MinuteCandle candle : minuteCandles) {
                LocalDateTime candleTimeKst = candle.getCandleDateTimeKst();
                if (targetDate.isAfter(candleTimeKst)) {
                    flag = false;
                    break;
                }
                switch (minuteType) {
                    case FIVE -> {
                        if (!fiveMinutesCandleRepository.existsByTimestampAndMarket(market.getType(), candle.getTimestamp())) {
                            fiveMinutesCandleRepository.save(FiveMinutesCandle.of(candle));
                            size ++;
                        } else {
                            flag = false;
                        }
                    }
                    default -> throw new RuntimeException("There is not a operate minute candle type : " + minuteType.name());
                }
            }
            nextTo = minuteCandles.get(minuteCandles.size() - 1).getCandleDateTimeUtc();
            long end = System.currentTimeMillis();
            System.out.printf("========== %s초 =========== 사이즈 : %s\r\n", (end - start) / 1000.0, size);
            Thread.sleep(100);
        }
    }
}
