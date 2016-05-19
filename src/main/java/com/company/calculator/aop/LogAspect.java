package com.company.calculator.aop;

import com.company.calculator.controllers.CalculationDataController;
import com.company.util.Util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.HashMap;

/**
 * Created by Yevhen on 26.04.2016.
 */

@Aspect
public class LogAspect {
    private static final String RESOURCE_LOG_EXCLUDE_MASK =
            "!(execution(* get*(..)) || execution(* set*(..)) || " +
            "execution(* com.company.calculator.library.Operation.operatorType()))";
    private static final String RESOURCE_LOG_CALCULATOR_LIBRARY_MASK =
            "(execution (* com.company.calculator.library..*(..)))";
    private static final String RESOURCE_LOG_CALCULATOR_LAUNCHER_MASK =
            "(execution (* com.company.calculator.launcher..*(..)))";
    private static final String RESOURCE_LOG_CALCULATOR_MODEL_MASK =
            "(execution (* com.company.calculator.model..*(..)))";
    private static final String RESOURCE_LOG_CALCULATOR_CONTROLLERS_MASK =
            "(execution (* com.company.calculator.controllers..*(..)))";
    private static final String RESOURCE_LOG_CALCULATOR_EXECUTE_MASK =
            "(execution (* com.company.calculator.library.Calculator.execute(..)))";
    private static final String RESOURCE_LOG_CALCULATOR_LAUNCHER_INTERACTIVE_CALCULATION_MASK =
            "(execution (* com.company.calculator.launcher.CalculatorLauncher.interactiveCalculation()))";
    private static final String RESOURCE_LOG_ALL_MASK =
            "(execution (* com.company..*(..)))" + " && " + RESOURCE_LOG_EXCLUDE_MASK;
    private static final String RESOURCE_LOG_INFO_MASK = "(" +
            RESOURCE_LOG_CALCULATOR_LIBRARY_MASK + "||" +
            RESOURCE_LOG_CALCULATOR_MODEL_MASK + "||" +
            RESOURCE_LOG_CALCULATOR_CONTROLLERS_MASK + "||" +
            RESOURCE_LOG_CALCULATOR_LAUNCHER_MASK + ")" +
            " && " + RESOURCE_LOG_EXCLUDE_MASK;
    private static final String RESOURCE_LOG_DEBUG_MASK = RESOURCE_LOG_ALL_MASK + "&& !(" + RESOURCE_LOG_INFO_MASK + ")";

    private HashMap<String, Long> executionTimeMap = new HashMap<>();

    private CalculationDataController calculationDataController;

    public void setCalculationDataController(CalculationDataController calculationDataController) {
        this.calculationDataController = calculationDataController;
    }

    private String methodFullName(JoinPoint joinPoint) {
        return AOPLogger.methodFullName(joinPoint);
    }

    private Long methodExecutionTime(JoinPoint joinPoint) {
        return executionTimeMap.get(methodFullName(joinPoint));
    }

    @Before(RESOURCE_LOG_ALL_MASK)
    public void onBefore(JoinPoint joinPoint) throws Throwable {
        // Calculate all temporary data beforehand of <before time> fixing because of needful precision of <before time>
        String methodFullName = methodFullName(joinPoint);
        // Store <before time>
        executionTimeMap.put(methodFullName, Util.getNanoTime());
    }

    @After(RESOURCE_LOG_ALL_MASK)
    public void onAfter(JoinPoint joinPoint) throws Throwable {
        // Fix <after time> (before of all other calculation)
        Long afterTime = Util.getNanoTime();
        // Get <full method name>
        String methodFullName = methodFullName(joinPoint);
        // Get <before time>
        Long beforeTime = executionTimeMap.get(methodFullName);
        // Store execution time for this method
        if (beforeTime != null) {
            executionTimeMap.put(methodFullName, afterTime - beforeTime);
        }
    }

    @AfterReturning(pointcut = RESOURCE_LOG_DEBUG_MASK, returning = "result")
    public void onAfterReturningDebug(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.debug(joinPoint, result, methodExecutionTime(joinPoint));
    }

    @AfterReturning(pointcut = RESOURCE_LOG_INFO_MASK, returning = "result")
    public void onAfterReturningInfo(JoinPoint joinPoint, Object result) throws Throwable {
        AOPLogger.info(joinPoint, result, methodExecutionTime(joinPoint));
    }

    @AfterThrowing(pointcut = RESOURCE_LOG_ALL_MASK, throwing = "throwable")
    public void onAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        AOPLogger.error(joinPoint, throwable, methodExecutionTime(joinPoint));
    }

    @AfterReturning(pointcut = RESOURCE_LOG_CALCULATOR_EXECUTE_MASK, returning = "result")
    public void onAfterReturningCalculatorExecute(JoinPoint joinPoint, Object result) throws Throwable {
        Object[] parameterValues = joinPoint.getArgs();

        calculationDataController.storeCalculationSuccess(parameterValues[0].toString(), result.toString(),
                Util.nanoToMicroTime(methodExecutionTime(joinPoint)));
    }

    @AfterThrowing(pointcut = RESOURCE_LOG_CALCULATOR_EXECUTE_MASK, throwing = "throwable")
    public void onAfterThrowingCalculatorExecute(JoinPoint joinPoint, Throwable throwable) {
        Object[] parameterValues = joinPoint.getArgs();

        calculationDataController.storeCalculationError(parameterValues[0].toString(), throwable.getMessage(),
                Util.nanoToMicroTime(methodExecutionTime(joinPoint)));
    }

    @Before(RESOURCE_LOG_CALCULATOR_LAUNCHER_INTERACTIVE_CALCULATION_MASK)
    public void onBeforeInteractiveCalculation(JoinPoint joinPoint) throws Throwable {
        calculationDataController.storeConnectEvent();
    }

    @After(RESOURCE_LOG_CALCULATOR_LAUNCHER_INTERACTIVE_CALCULATION_MASK)
    public void onAfterInteractiveCalculation(JoinPoint joinPoint) throws Throwable {
        calculationDataController.storeDisconnectEvent();
    }
}
