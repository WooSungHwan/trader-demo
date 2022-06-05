package com.example.traderdemo.app.backtest.strategy.bid;

import com.trader.common.indicators.result.RSIs;

/**
 * 매수 전략
 */
public interface BidStrategy {
    boolean isBid(RSIs rsi);
}
