package com.company.calculator.aop;

import com.company.util.Util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.ArrayDeque;

/**
 * Created by Yevhen on 26.04.2016.
 */

@Aspect
public class LogAspect {
    private ArrayDeque<Long> startTimeStack = new ArrayDeque<>();
    private long lastMethodExecutionNanoTime;

    @Before("execution (public * com.company.calculator.library..*(..))")
    public void onBefore(JoinPoint joinPoint) throws Throwable {
        startTimeStack.push(Util.getNanoTime());
    }

    @After("execution (public * com.company.calculator.library..*(..))")
    public void onAfter(JoinPoint joinPoint) throws Throwable {
        lastMethodExecutionNanoTime = (Util.getNanoTime() - startTimeStack.pop());
    }

    @AfterReturning(pointcut = "execution (public * com.company.calculator.library..*(..))", returning = "result")
    public void onAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.info(joinPoint, result, lastMethodExecutionNanoTime);
    }

    @AfterThrowing(pointcut = "execution (public * com.company.calculator.library..*(..))", throwing = "throwable")
    public void onAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        AOPLogger.error(joinPoint, throwable, lastMethodExecutionNanoTime);
    }
}
