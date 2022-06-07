package com.example.traderdemo.app.backtest;

import com.example.traderdemo.app.backtest.collection.WalletList;
import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import com.example.traderdemo.app.backtest.service.CandleService;
import com.example.traderdemo.app.backtest.service.OrderService;
import com.example.traderdemo.app.backtest.service.WalletService;
import com.example.traderdemo.app.backtest.strategy.ask.AskStrategy;
import com.example.traderdemo.app.backtest.strategy.ask.implementations.RSIAskStrategy;
import com.example.traderdemo.app.backtest.strategy.bid.BidStrategy;
import com.example.traderdemo.app.backtest.strategy.bid.implementations.RSIBidStrategy;
import com.example.traderdemo.app.common.annotation.Exit;
import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import com.example.traderdemo.app.common.enums.MarketType;
import com.trader.common.indicators.result.RSIs;
import com.trader.common.utils.IndicatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.traderdemo.app.backtest.BackTestConst.BID_SLOT;
import static com.example.traderdemo.app.backtest.BackTestConst.START_BALANCE;
import static com.example.traderdemo.app.common.enums.MarketType.KRW_BTC;
import static com.example.traderdemo.app.common.utils.WalletListUtil.isAskable;
import static com.example.traderdemo.app.common.utils.WalletListUtil.isBidable;

@Slf4j
@RequiredArgsConstructor
@Component
public class BackTester {

    private final WalletService walletService;
    private final OrderService orderService;
    private final CandleService candleService;

    @Exit
    public void run() {
        //TODO 너무 금방끝날듯?
        final LocalDateTime start = LocalDateTime.of(2022, 5, 1, 17, 0, 0); // 16 40 정도가 200개가 가져올 수 있는 데이터임.
        final LocalDateTime end = LocalDateTime.of(2022, 5, 31, 23, 59, 55);
        final MarketType market = KRW_BTC;

        // 기존 백테스트 내역은 삭제.
        orderService.deleteAll();
        walletService.deleteAll();
        // 신규 백테스트 수행할 지갑생성.
        walletService.addInitialWallet(market, START_BALANCE, BID_SLOT);

        boolean over = false;
        int limit = 200;
        LocalDateTime targetDate = LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0, 0);
        while (!over) {
            FiveMinutesCandle baseCandle = candleService.findFiveMinutesCandleByKst(market.getType(), targetDate);
            if (Objects.isNull(baseCandle) || Objects.isNull(baseCandle.getTimestamp())) {
                continue;
            }

            List<FiveMinutesCandle> candles = candleService.findFiveMinutesCandlesUnderByTimestamp(market.getType(), baseCandle.getTimestamp(), limit);
            if (candles.size() < limit) {
                log.info("해당 시간대는 캔들 {}개 미만이므로 테스트할 수 없습니다. -- {}", limit, baseCandle.getCandleDateTimeKst());
                continue;
            }

            // targetCandle의 봉에서 매수, 매도
            FiveMinutesCandle targetCandle = candleService.nextCandle(baseCandle.getMarket().getType(), baseCandle.getTimestamp());

            List<AccountCoinWallet> wallets = walletService.findByMarket(market);
            WalletList walletList = WalletList.of(wallets);

            List<Double> prices = candles.stream().map(FiveMinutesCandle::getTradePrice).collect(Collectors.toUnmodifiableList());
            RSIs rsi14 = IndicatorUtil.getRSI14(prices);
//            BollingerBands bollingerBands = IndicatorUtil.getBollingerBands(prices);

            boolean isAskable = isAskable(walletList);
            boolean isBidable = isBidable(walletList);

            // 해당 코인이 매수할수도 매도할수도 없는 상태면 넘겨
            if (!isAskable && !isBidable) {
                continue;
            }

            // rsi 매도전략
            AskStrategy askStrategy = new RSIAskStrategy();
            if (isAskable && askStrategy.isAsk(rsi14)) {
                // 매도
                for (AccountCoinWallet wallet : walletList.getAskableWallets()) {
                    log.info("{} 현재 캔들", targetCandle.getCandleDateTimeKst());
                    orderService.ask(targetCandle, wallet);
                }
            }

            // rsi 매수 전략
            BidStrategy bidStrategy = new RSIBidStrategy();
            if (isBidable && bidStrategy.isBid(rsi14)) {
                // 매수
                orderService.bid(targetCandle, walletList.getBidableWallet());
            }

            // 종가로 fetch
            List<AccountCoinWallet> fetchWallets = walletService.fetchWallet(market, targetCandle.getTradePrice());
            WalletList result = WalletList.of(fetchWallets);
            printWalletInfo(result);

            // 기간종료
            if (end.isBefore(targetCandle.getCandleDateTimeKst())) {
                over = true;
            }
            targetDate = targetDate.plusMinutes(5);
        }
    }

    private void printWalletInfo(WalletList result) {
        if (result.isNotEmpty()) {
            log.info(result.getWalletSummaryInfo());
        }
    }
}
