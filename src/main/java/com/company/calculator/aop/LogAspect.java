package com.company.calculator.aop;

import com.company.calculator.controllers.CalculationDataController;
import com.company.calculator.model.jdbc.JdbcCalculationDataDao;
import com.company.util.Util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.ArrayDeque;

/**
 * Created by Yevhen on 26.04.2016.
 */

@Aspect
public class LogAspect {
    private static final String RESOURCE_LOG_CALCULATOR_EXECUTE_MASK =
            "(execution(* com.company.calculator.library.Calculator.execute(..)))";
    private static final String RESOURCE_LOG_MODEL_MASK =
            "(execution (public * com.company.calculator.model..*(..)))";
    private static final String RESOURCE_LOG_EXCLUDE_MASK = "" +
            "!(execution(* com.company.calculator.library.Operation.getOperationCode()) || " +
            "execution(* com.company.calculator.library.Calculator.operationCodeSet()))";

    private static final String RESOURCE_LOG_INFO_MASK = "(" + RESOURCE_LOG_CALCULATOR_EXECUTE_MASK + "||" + RESOURCE_LOG_MODEL_MASK + ")";
    private static final String RESOURCE_LOG_ALL_MASK =
            "(execution (public * com.company.calculator.library..*(..)) || " +
                    "execution (public * com.company.calculator.launcher..*(..))) && " + RESOURCE_LOG_EXCLUDE_MASK;
    private static final String RESOURCE_LOG_DEBUG_MASK = RESOURCE_LOG_ALL_MASK + "&& !" + RESOURCE_LOG_INFO_MASK;

    private ArrayDeque<Long> startTimeStack = new ArrayDeque<>();
    private long lastMethodExecutionNanoTime;

    private CalculationDataController calculationDataController;

    public void setCalculationDataController(CalculationDataController calculationDataController) {
        this.calculationDataController = calculationDataController;
    }

    @Before(RESOURCE_LOG_ALL_MASK)
    public void onBefore(JoinPoint joinPoint) throws Throwable {
        startTimeStack.push(Util.getNanoTime());
    }

    @After(RESOURCE_LOG_ALL_MASK)
    public void onAfter(JoinPoint joinPoint) throws Throwable {
        lastMethodExecutionNanoTime = (Util.getNanoTime() - startTimeStack.pop());
    }

    @AfterReturning(pointcut = RESOURCE_LOG_DEBUG_MASK, returning = "result")
    public void onAfterReturningDebug(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.debug(joinPoint, result, lastMethodExecutionNanoTime);
    }

    @AfterReturning(pointcut = RESOURCE_LOG_INFO_MASK, returning = "result")
    public void onAfterReturningInfo(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.info(joinPoint, result, lastMethodExecutionNanoTime);
    }

    @AfterThrowing(pointcut = RESOURCE_LOG_ALL_MASK, throwing = "throwable")
    public void onAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        AOPLogger.error(joinPoint, throwable, lastMethodExecutionNanoTime);
    }


    @AfterReturning(pointcut = RESOURCE_LOG_CALCULATOR_EXECUTE_MASK, returning = "result")
    public void onAfterReturningCalculatorExecute(JoinPoint joinPoint, Object result) throws Throwable {
        Object[] parameterValues = joinPoint.getArgs();

        calculationDataController.storeCalculationSuccess(parameterValues[0].toString(), result.toString(),
                Util.nanoToMicroTime(lastMethodExecutionNanoTime));
    }

    @AfterThrowing(pointcut = RESOURCE_LOG_CALCULATOR_EXECUTE_MASK, throwing = "throwable")
    public void onAfterThrowingCalculatorExecute(JoinPoint joinPoint, Throwable throwable) {
        Object[] parameterValues = joinPoint.getArgs();

        calculationDataController.storeCalculationError(parameterValues[0].toString(), throwable.getMessage(),
                Util.nanoToMicroTime(lastMethodExecutionNanoTime));
    }
}
