package com.example.traderdemo.app.backtest.strategy.bid;

import com.trader.common.indicators.result.RSIs;

/**
 * ๋งค์ ์ ๋ต
 */
public interface BidStrategy {
    boolean isBid(RSIs rsi);
}
