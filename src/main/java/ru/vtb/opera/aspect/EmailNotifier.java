package ru.vtb.opera.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vtb.opera.service.OperaEmailService;

@Aspect
@Component
public class EmailNotifier {
    @Autowired
    OperaEmailService operaEmailService;

//    @Pointcut("execution(* ru.vtb.opera.service.OperaService.buyTicket(..)) " +
//            "|| execution(* ru.vtb.opera.service.OperaService.returnTicket(..))")
    @Pointcut("@annotation(EmailAnnotation)")
    public void callAtOperaServicePublic() {}

    @After("callAtOperaServicePublic()")
    public void afterCallAtMethod(JoinPoint jp) {
        operaEmailService.sendSimpleEmail("natal.medv@mail.ru", "opera", jp.getSignature().getName());
    }
}
