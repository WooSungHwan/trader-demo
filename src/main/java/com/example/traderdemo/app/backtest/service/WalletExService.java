package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class WalletExService {

    private final WalletService walletService;

    @Transactional
    public void save(AccountCoinWallet wallet) {
        walletService.save(wallet);
    }
}
