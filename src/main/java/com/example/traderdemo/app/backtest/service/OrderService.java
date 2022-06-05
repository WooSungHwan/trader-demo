package com.example.traderdemo.app.backtest.service;

import com.example.traderdemo.app.backtest.repository.BackTestOrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final BackTestOrdersRepository ordersRepository;

    @Transactional
    public void deleteAll() {
        ordersRepository.deleteAll();
    }
}
