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
    private static final String RESOURCE_LOG_MASK =
            "(execution (public * com.company.calculator.library..*(..)) || " +
                    "execution (public * com.company.calculator.launcher..*(..))) && " +
                    "!execution(* com.company.calculator.library.Operation.getOperationCode()) &&" +
                    "!execution(* com.company.calculator.library.Calculator.operationCodeSet())";

    @Before(RESOURCE_LOG_MASK)
    public void onBefore(JoinPoint joinPoint) throws Throwable {
        startTimeStack.push(Util.getNanoTime());
    }

    @After(RESOURCE_LOG_MASK)
    public void onAfter(JoinPoint joinPoint) throws Throwable {
        lastMethodExecutionNanoTime = (Util.getNanoTime() - startTimeStack.pop());
    }

    @AfterReturning(pointcut = RESOURCE_LOG_MASK, returning = "result")
    public void onAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.info(joinPoint, result, lastMethodExecutionNanoTime);
    }

    @AfterThrowing(pointcut = RESOURCE_LOG_MASK, throwing = "throwable")
    public void onAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        AOPLogger.error(joinPoint, throwable, lastMethodExecutionNanoTime);
    }
}
