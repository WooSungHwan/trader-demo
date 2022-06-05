package com.example.traderdemo;

import com.example.traderdemo.config.ApplicationMode;
import com.example.traderdemo.upbit.UpbitProperties;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.TimeZone;

@EnableConfigurationProperties({UpbitProperties.class, ApplicationMode.class})
@SpringBootApplication
public class TraderDemoApplication {

    public static DecimalFormat df = new DecimalFormat("###,###"); // 출력 숫자 포맷

    public static void main(String[] args) {
        SpringApplication.run(TraderDemoApplication.class, args);
    }

    @PostConstruct
    private void postConstruct() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
