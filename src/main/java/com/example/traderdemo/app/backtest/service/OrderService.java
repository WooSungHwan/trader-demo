package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import com.example.traderdemo.app.backtest.entity.BackTestOrders;
import com.example.traderdemo.app.backtest.repository.BackTestOrdersRepository;
import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import com.trader.common.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.trader.common.utils.IndicatorUtil.fee;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final BackTestOrdersRepository repository;
    private final WalletExService walletService;

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Transactional
    public BackTestOrders ask(FiveMinutesCandle targetCandle, AccountCoinWallet wallet) {
        if (wallet.isEmpty()) {
            return null;
        }
        double volume = wallet.getVolume();
        double valAmount = targetCandle.getTradePrice() * volume;
        double fee = fee(valAmount);
        double proceeds = valAmount - wallet.getAllPrice();
        double proceedRate = proceeds / wallet.getAllPrice() * 100;
        double maxProceedRate = NumberUtils.max(wallet.getMaxProceedRate(), proceedRate);

        BackTestOrders order = repository.save(BackTestOrders.askOf(
                wallet.getMarket(),
                targetCandle.getOpeningPrice(),
                volume, fee, targetCandle.getTimestamp(),
                proceeds,
                proceedRate,
                maxProceedRate));

        // 매도 -> 다음 캔들 시가에 매도
        wallet.allAsk(valAmount, fee);
        walletService.save(wallet);
        return order;
    }

    @Transactional
    public BackTestOrders bid(FiveMinutesCandle targetCandle, AccountCoinWallet wallet) {
        final double openingPrice = targetCandle.getOpeningPrice();
        final Double bidBalance = wallet.getBalance();
        final double fee = fee(bidBalance);
        final double bidAmount = bidBalance;
        final double volume = bidAmount / openingPrice; // 매수량

        // 다음 캔들 시가에 매수
        BackTestOrders order = repository.save(BackTestOrders.bidOf(
                targetCandle.getMarket(),
                openingPrice,
                volume, fee, targetCandle.getTimestamp()));

        wallet.allBid(openingPrice, bidAmount, volume, fee, targetCandle.getCandleDateTimeUtc());
        walletService.save(wallet);
        return order;
    }
}
