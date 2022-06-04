package com.example.traderdemo.config.aop;

import com.example.traderdemo.app.common.annotation.Exit;
import com.example.traderdemo.app.common.annotation.ExitThrowing;
import com.example.traderdemo.config.ApplicationTerminator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class ExitAspect {

    private final ApplicationTerminator terminator;

    @AfterReturning("@annotation(exit)")
    public void exit(Exit exit) {
        log.info("AfterReturning exit");
        terminator.exit();
    }

    @AfterThrowing("@annotation(exit)")
    public void exitIfThrowing(ExitThrowing exit) {
        log.info("AfterThrowing exit");
        terminator.exit();
    }

}
