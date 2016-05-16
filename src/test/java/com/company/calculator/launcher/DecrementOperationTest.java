package com.company.calculator.launcher;

import org.junit.BeforeClass;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class DecrementOperationTest extends IntegerTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new DecrementOperation());
    }

    @Override
    protected double calcExpected(double operand_1, double operand_2) {
        return Math.round(operand_1) - 1;
    }
}