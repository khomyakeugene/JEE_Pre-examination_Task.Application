package com.company.calculator.launcher;

import com.company.util.Factorial;
import org.junit.BeforeClass;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class FactorialOperationTest extends IntegerTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new FactorialOperation());
    }

    @Override
    // Sort of compromise: the method <calcExpected> will be never called in the overridden method <expectedResult>
    // and need to implement just because of the inheritance of the <IntegerTest>-class
    protected double calcExpected(double operand_1, double  operand_2) {
        return Double.MIN_VALUE;
    }

    @Override
    protected String expectedResult() {
        return Factorial.factorial(Long.parseLong(operand_1)).toString();
    }
}