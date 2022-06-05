package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import com.example.traderdemo.app.common.repository.FiveMinutesCandleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class CandleService {

    private final FiveMinutesCandleRepository fiveMinutesCandleRepository;

    public FiveMinutesCandle findFiveMinutesCandleByKst(String market, LocalDateTime targetDate) {
        return fiveMinutesCandleRepository.findFiveMinutesCandleByKst(market, targetDate);
    }

    public List<FiveMinutesCandle> findFiveMinutesCandlesUnderByTimestamp(String market, Long timestamp, int limit) {
        return fiveMinutesCandleRepository.findFiveMinutesCandlesUnderByTimestamp(market, timestamp, limit);
    }

    public FiveMinutesCandle nextCandle(String market, Long timestamp) {
        return fiveMinutesCandleRepository.nextCandle(timestamp, market);
    }
}
