package com.bank.server.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect @Component
public class LoggingAspect {

    @After("execution(* com.bank.server.rest.BankController.get* (..))")
    public void controllerGetAccountInfoLogger() {
        log.info("Retrieving Account Information");
    }
    @After("execution(* com.bank.server.rest.BankController.createAccount (..))")
    public void controllerCreateNewAccountLogger() {
        log.info("Creating New Account");
    }
    @After("execution(* com.bank.server.rest.BankController.deleteAccount (..))")
    public void controllerDeleteAccountLogger() {
        log.info("Deleting Account");
    }
    @After("execution(* com.bank.server.rest.BankController.patchAccountInfo(..))")
    public void controllerPatchAccountLogger() {
        log.info("Patching Account info");
    }

}
