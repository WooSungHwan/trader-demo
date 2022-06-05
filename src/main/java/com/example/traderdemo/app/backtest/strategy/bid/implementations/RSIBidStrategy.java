package com.example.traderdemo.app.backtest.strategy.bid.implementations;

import com.example.traderdemo.app.backtest.strategy.bid.BidStrategy;
import com.trader.common.indicators.result.RSIs;

import java.util.List;
import java.util.Objects;

public class RSIBidStrategy implements BidStrategy {

    // RSI 매수 임계 수치
    private final double RSI_BID_VALUE = 30.0d;

    @Override
    public boolean isBid(RSIs rsi) {
        if (Objects.isNull(rsi)) {
            return false;
        }

        List<Double> rawRSI = rsi.getRsi();
        Double nowRSI = rawRSI.get(0);
        return nowRSI >= RSI_BID_VALUE;
    }

}
