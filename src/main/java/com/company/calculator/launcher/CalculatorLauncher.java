package com.company.calculator.launcher;

import com.company.calculator.library.Operation;
import com.company.calculator.library.SimpleCalculator;
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

    public void interactiveCalculation(SimpleCalculator simpleCalculator) {
        String expression;

        Set<String> operationCodeSet = simpleCalculator.operationCodeSet();
        String[] operationCodeList = operationCodeSet.toArray(new String[operationCodeSet.size()]);

        do {
            expression = Util.readInputString(String.format(PROMPT_MESSAGE,
                    Arrays.toString(operationCodeList)));
            if (!expression.isEmpty()) {
                try {
                    Util.printMessage(String.format(RESULT_PATTERN, expression, simpleCalculator.execute(expression.trim())));
                } catch (IllegalArgumentException e) {
                    Util.printMessage(e.getMessage());
                }
            }
        } while (!expression.isEmpty());

        Util.printMessage(FAREWELL_MESSAGE);
    }
}
