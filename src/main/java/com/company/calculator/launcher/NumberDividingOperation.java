package com.company.calculator.launcher;

import com.company.calculator.library.BinaryNumberOperation;
import com.company.calculator.library.Operation;

/**
 * Created by Yevhen on 23.04.2016.
 */
public class NumberDividingOperation extends BinaryNumberOperation implements Operation {
    @Override
    protected String calculate() {
        return Double.toString(getDoubleOperand(0) / getDoubleOperand(1));
    }
}
