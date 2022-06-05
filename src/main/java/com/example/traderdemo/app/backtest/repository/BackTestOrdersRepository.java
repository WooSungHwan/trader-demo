package com.example.traderdemo.app.backtest.repository;

import com.example.traderdemo.app.backtest.entity.BackTestOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackTestOrdersRepository extends JpaRepository<BackTestOrders, Long> {
}
