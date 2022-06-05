package com.example.traderdemo.app.backtest.entity;

import com.example.traderdemo.app.common.enums.MarketType;
import com.trader.common.enums.OrdSideType;
import com.trader.common.indicators.result.RSIs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.trader.common.enums.OrdSideType.ASK;
import static com.trader.common.enums.OrdSideType.BID;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table
@Entity
public class BackTestOrders {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "market", length = 10)
    private MarketType market;

    @Column(name = "side")
    private OrdSideType side; // 매도/매수

    @Column(name = "price")
    private Double price; // 1코인 당 거래가격

    @Column(name = "volume")
    private Double volume; // 거래량

    @Column(name = "fee")
    private Double fee; // 수수료

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "timestamp")
    private long timestamp; // 매수한 캔들의 타임스탬프

    @Column(name = "proceeds")
    private Double proceeds; // 수익금

    @Column(name = "proceed_rate")
    private Double proceedRate; // 수익률

    @Column(name = "max_proceed_rate")
    private Double maxProceedRate; // 최대 수익률

    @Column(name = "rsi")
    private Double rsi;

    public static BackTestOrders askOf(MarketType market, Double price, Double volume, Double fee, long timestamp,
                                       Double proceeds, Double proceedRate, Double maxProceedRate, RSIs rsi) {
        return BackTestOrders.builder()
                .market(market)
                .side(ASK)
                .price(price)
                .volume(volume)
                .fee(fee)
                .createdAt(LocalDateTime.now())
                .timestamp(timestamp)
                .proceeds(proceeds)
                .proceedRate(proceedRate)
                .maxProceedRate(maxProceedRate)
                .rsi(rsi.getRsi().get(0))
                .build();
    }

    public static BackTestOrders bidOf(MarketType market, Double price, Double volume, Double fee, long timestamp
            , RSIs rsi) {
        return BackTestOrders.builder()
                .market(market)
                .side(BID)
                .price(price)
                .volume(volume)
                .fee(fee)
                .createdAt(LocalDateTime.now())
                .timestamp(timestamp)
                .rsi(rsi.getRsi().get(0))
                .build();
    }

}
