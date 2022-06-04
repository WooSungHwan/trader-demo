package com.example.traderdemo.upbit;

import com.example.traderdemo.app.common.enums.MarketType;
import com.trader.common.utils.MinuteCandle;

import java.time.LocalDateTime;
import java.util.List;

public interface UpbitCandleClient {

    List<MinuteCandle> getMinuteCandles(int minutes, MarketType market, int count, LocalDateTime to);

}