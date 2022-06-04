package com.example.traderdemo.app.common.repository;

import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FiveMinutesCandleRepository extends JpaRepository<FiveMinutesCandle, Long> {

    @Query(nativeQuery = true,
           value = "select if(exists(select 1 from five_minutes_candle where market = :market and timestamp = :timestamp), 'true', 'false') as result ")
    boolean existsByTimestampAndMarket(@Param("market") String market,
                                       @Param("timestamp") Long timestamp);
}