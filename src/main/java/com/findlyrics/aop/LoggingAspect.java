package com.findlyrics.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Oleksandr_Kramskyi on 9/29/2014.
 */

@Aspect
public class LoggingAspect {

    /*@Around("execution(* *(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        System.out.println("called method : " + MethodSignature.class.cast(point.getSignature()).getMethod().getName());
        return result;
    }*/

//    @Pointcut("execution(* HelloWorld.sayHello(..))")
    @Pointcut("execution(* ArtistDAO.getArtist(..))")
    public void logging() {}

    @Around("logging()")
    public Object logging(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("Before " + thisJoinPoint.getSignature());
        Object ret = thisJoinPoint.proceed();
        System.out.println("After " + thisJoinPoint.getSignature());

        return ret;
    }
}
