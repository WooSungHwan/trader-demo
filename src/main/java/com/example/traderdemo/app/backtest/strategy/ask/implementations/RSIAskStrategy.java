package com.example.traderdemo.app.backtest.strategy.ask.implementations;

import com.example.traderdemo.app.backtest.strategy.ask.AskStrategy;
import com.trader.common.indicators.result.RSIs;

import java.util.List;
import java.util.Objects;

/**
 *  RSI 매도 전략
 */
public class RSIAskStrategy implements AskStrategy {
    // RSI 매도 임계 수치
    private final double RSI_ASK_VALUE = 70.0d;

    @Override
    public boolean isAsk(RSIs rsi) {
        if (Objects.isNull(rsi)) {
            return false;
        }

        List<Double> rawRSI = rsi.getRsi();
        Double nowRSI = rawRSI.get(0);
        return nowRSI >= RSI_ASK_VALUE;
    }
}
