package com.example.traderdemo.app.common.repository;

import com.example.traderdemo.app.common.entity.FiveMinutesCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FiveMinutesCandleRepository extends JpaRepository<FiveMinutesCandle, Long> {

    @Query(nativeQuery = true,
           value = "select if(exists(select 1 from five_minutes_candle where market = :market and timestamp = :timestamp), 'true', 'false') as result ")
    boolean existsByTimestampAndMarket(@Param("market") String market,
                                       @Param("timestamp") Long timestamp);

    /**
     * 캔들 시간순서대로 진행하기 위해 사용.
     * @param market
     * @param kst
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * FROM five_minutes_candle WHERE market = :market AND candle_date_time_kst = :kst")
    FiveMinutesCandle findFiveMinutesCandleByKst(@Param("market") String market,
                                                 @Param("kst") LocalDateTime kst);

    /**
     * 해당 시점을 포함하여 이전 캔들 200개 가져온다.
     * @param timestamp
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * FROM five_minutes_candle WHERE market = :market AND timestamp <= :timestamp ORDER BY timestamp DESC LIMIT :limit")
    List<FiveMinutesCandle> findFiveMinutesCandlesUnderByTimestamp(@Param("market") String market,
                                                                   @Param("timestamp") long timestamp,
                                                                   @Param("limit") int limit);

    /**
     * 해당 timestamp값의 다음 캔들을 가져온다.
     * @param timestamp
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * FROM five_minutes_candle WHERE market = :market AND timestamp > :timestamp ORDER BY timestamp LIMIT 1")
    FiveMinutesCandle nextCandle(@Param("timestamp") long timestamp,
                                 @Param("market") String market);
}