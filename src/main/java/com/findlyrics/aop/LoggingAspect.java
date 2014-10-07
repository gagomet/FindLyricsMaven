package com.findlyrics.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Oleksandr_Kramskyi on 9/29/2014.
 */

@Aspect
public class LoggingAspect {

    /*@Pointcut("execution(public * com.findlyrics.db.dao.impl.ArtistDAO.*(..))")
    public void logging() {
    }*/

    @Around("execution(public * com.findlyrics.db.dao.*.*(..))")
    public Object logging(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("Before " + thisJoinPoint.getSignature());
        Object ret = thisJoinPoint.proceed();
        System.out.println("After " + thisJoinPoint.getSignature());

        return ret;
    }
}
