package com.company.calculator.launcher;

import com.company.calculator.library.Calculator;
import com.company.util.Util;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Yevhen on 23.04.2016.
 */
public class CalculatorLauncher {
    private static final String PROMPT_MESSAGE = "You can execute the following operations: %s. Please, enter a formula (or press only <Enter> to stop):";
    private static final String FAREWELL_MESSAGE = "Thank you for use our calculator";
    private static final String RESULT_PATTERN = "%s = %s";

    Calculator calculator;

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    private String[] operationCodeList() {
        Set<String> operationCodeSet = calculator.operationCodeSet();

        return operationCodeSet.toArray(new String[operationCodeSet.size()]);
    }

    public void interactiveCalculation() {
        String expression;

        do {
            expression = Util.readInputString(String.format(PROMPT_MESSAGE, Arrays.toString(operationCodeList())));
            if (!expression.isEmpty()) {
                try {
                    Util.printMessage(String.format(RESULT_PATTERN, expression, calculator.execute(expression.trim())));
                } catch (IllegalArgumentException e) {
                    Util.printMessage(e.getMessage());
                }
            }
        } while (!expression.isEmpty());

        Util.printMessage(FAREWELL_MESSAGE);
    }


    public void init() {
        // Till now - empty method using just for the possibility to "hang" here the "aspect"-functionality, such as
        // fixation if user-login in the database. Probably, it is not the most method to inject the functionality,
        // but let use this to support "common aspect" model approcah
    }
}
