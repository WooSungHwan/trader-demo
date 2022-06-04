package com.example.traderdemo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "application")
public class ApplicationMode {

    @NotBlank(message = "Application Mode를 설정해주세요.")
    private String mode;

    public ApplicationModeType getApplicationMode() {
        return ApplicationModeType.find(mode);
    }

}
