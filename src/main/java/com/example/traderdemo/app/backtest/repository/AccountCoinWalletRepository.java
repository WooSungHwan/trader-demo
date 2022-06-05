package com.example.traderdemo.app.backtest.repository;

import com.example.traderdemo.app.backtest.entity.AccountCoinWallet;
import com.example.traderdemo.app.common.enums.MarketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountCoinWalletRepository extends JpaRepository<AccountCoinWallet, Long> {

    List<AccountCoinWallet> findByMarket(@Param("market") MarketType market);

}
