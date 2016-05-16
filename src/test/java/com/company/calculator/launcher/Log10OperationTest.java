package com.company.calculator.launcher;

import org.junit.BeforeClass;

/**
 * Created by Yevhen on 23.04.2016.
 */
public class Log10OperationTest extends NumberTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new Log10Operation());
    }

    @Override
    protected double calcExpected(double operand_1, double operand_2) {
        return Math.log10(operand_1);
    }
}