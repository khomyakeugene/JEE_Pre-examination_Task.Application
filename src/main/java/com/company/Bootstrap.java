package com.company;

import com.company.calculator.launcher.CalculatorLauncher;
import com.company.calculator.library.SimpleCalculator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {
    private final static String APPLICATION_CONTEXT_NAME = "application-context.xml";
    private final static String CALCULATOR_BEAN_NAME = "simpleCalculator";

    public static void main(String[] args) {
        new CalculatorLauncher().interactiveCalculation(
                new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_NAME).
                        getBean(CALCULATOR_BEAN_NAME, SimpleCalculator.class));
    }
}