package com.company.calculator.config;

import com.company.calculator.aop.LogAspect;
import com.company.calculator.launcher.*;
import com.company.calculator.library.SimpleCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Yevhen on 23.04.2016.
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AppConfig {
    private static final String ADDITION_OPERATION_CODE = "+";
    private static final String SUBTRACT_OPERATION_CODE = "-";
    private static final String MULTIPLICATION_OPERATION_CODE = "*";
    private static final String DIVIDING_OPERATION_CODE = "/";
    private static final String SQUARE_ROOT_CODE = "sqrt";
    private static final String LOG10_CODE = "log10";
    private static final String LOG_CODE = "log";
    private static final String INCREMENT_CODE = "++";
    private static final String DECREMENT_CODE = "--";
    private static final String FACTORIAL_CODE = "!";

    // To support aop-logging
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public SimpleCalculator simpleCalculator () {
        SimpleCalculator simpleCalculator = new SimpleCalculator();

        simpleCalculator.addOperation(MULTIPLICATION_OPERATION_CODE, new NumberMultiplicationOperation());
        simpleCalculator.addOperation(DIVIDING_OPERATION_CODE, new NumberDividingOperation());
        simpleCalculator.addOperation(SQUARE_ROOT_CODE, new SquareRootOperation());
        simpleCalculator.addOperation(LOG10_CODE, new Log10Operation());
        simpleCalculator.addOperation(LOG_CODE, new LogOperation());
        simpleCalculator.addOperation(INCREMENT_CODE, new IncrementOperation());
        simpleCalculator.addOperation(DECREMENT_CODE, new DecrementOperation());
        simpleCalculator.addOperation(FACTORIAL_CODE, new FactorialOperation());
        simpleCalculator.addOperation(ADDITION_OPERATION_CODE, new DateAdditionOperation());
        simpleCalculator.addOperation(SUBTRACT_OPERATION_CODE, new DateSubtractOperation());

        return simpleCalculator;
    }
}


