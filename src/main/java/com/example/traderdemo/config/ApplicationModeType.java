package com.example.traderdemo.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.common.enums.config.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ApplicationModeType implements EnumInterface {
    BACK_TEST("BACK_TEST", "백테스트모드"),
    REAL_TRADE("REAL_TRADE", "운영모드"),
    CRAWL("CRAWL", "크롤링모드");

    private String type;
    private String name;

    public static ApplicationModeType find(String type) {
        return EnumInterface.find(type, values());
    }

    @JsonCreator
    public static ApplicationModeType findToNull(String type) {
        return EnumInterface.findToNull(type, values());
    }

}
