package com.company;

import com.company.calculator.launcher.CalculatorLauncher;
import com.company.calculator.library.SimpleCalculator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {
    private final static String APPLICATION_CONTEXT_NAME = "application-context.xml";
    private final static String CALCULATOR_LAUNCHER_BEAN_NAME = "calculatorLauncher";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_NAME);
        CalculatorLauncher calculatorLauncher = context.getBean(CALCULATOR_LAUNCHER_BEAN_NAME, CalculatorLauncher.class);
        //calculatorLauncher.init();
        calculatorLauncher.interactiveCalculation();
    }
}