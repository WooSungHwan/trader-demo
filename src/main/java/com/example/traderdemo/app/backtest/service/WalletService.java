package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import com.example.traderdemo.app.backtest.repository.AccountCoinWalletRepository;
import com.example.traderdemo.app.common.enums.MarketType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.traderdemo.app.backtest.BackTestConst.BID_SLOT;
import static com.example.traderdemo.app.backtest.BackTestConst.START_BALANCE;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final AccountCoinWalletRepository accountCoinWalletRepository;

    @Transactional
    public void deleteAll() {
        accountCoinWalletRepository.deleteAll();
    }

    @Transactional
    public void addInitialWallet(MarketType market, double balance, int bidSlot) {
        List<AccountCoinWallet> wallets = new ArrayList<>();
        for(int i = 0; i < bidSlot; i++) {
            wallets.add(AccountCoinWallet.of(market, balance * market.getPercent() / bidSlot));
        }
        accountCoinWalletRepository.saveAll(wallets);
    }

    public List<AccountCoinWallet> findByMarket(MarketType market) {
        return accountCoinWalletRepository.findByMarket(market);
    }
}
