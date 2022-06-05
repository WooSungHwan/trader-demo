package com.example.traderdemo.app.backtest.strategy.ask;

import com.trader.common.indicators.result.RSIs;

public interface AskStrategy {
    boolean isAsk(RSIs rsi);
}
