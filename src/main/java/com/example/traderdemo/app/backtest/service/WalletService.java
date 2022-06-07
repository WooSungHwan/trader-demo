package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import com.example.traderdemo.app.backtest.repository.AccountCoinWalletRepository;
import com.example.traderdemo.app.common.enums.MarketType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final AccountCoinWalletRepository repository;

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Transactional
    public void addInitialWallet(MarketType market, double balance, int bidSlot) {
        List<AccountCoinWallet> wallets = new ArrayList<>();
        for(int i = 0; i < bidSlot; i++) {
            wallets.add(AccountCoinWallet.of(market, balance * market.getPercent() / bidSlot));
        }
        repository.saveAll(wallets);
    }

    public List<AccountCoinWallet> findByMarket(MarketType market) {
        return repository.findByMarket(market);
    }

    @Transactional
    public void save(AccountCoinWallet wallet) {
        repository.save(wallet);
    }

    @Transactional
    public List<AccountCoinWallet> fetchWallet(MarketType market, Double tradePrice) {
        List<AccountCoinWallet> wallets = repository.findByMarket(market);
        wallets.forEach(wallet -> {
            if (!wallet.isEmpty()) {
                wallet.fetch(tradePrice);
            }
        });
        return wallets;
    }
}
