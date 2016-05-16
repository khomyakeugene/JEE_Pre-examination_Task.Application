package com.company.calculator.launcher;

import com.company.calculator.library.Operation;
import com.company.calculator.library.UnaryIntegerOperation;

/**
 * Created by Yevhen on 23.04.2016.
 */
public class IncrementOperation extends UnaryIntegerOperation implements Operation {
    @Override
    protected String calculate() {
        return Long.toString(getIntegerOperand(0)+1);
    }
}
