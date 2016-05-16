package com.company.calculator.launcher;

import org.junit.BeforeClass;

/**
 * Created by Yevhen on 25.04.2016.
 */
public class DateSubtractOperationDateAndIntegerTest extends DateOperationDateAndIntegerTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new DateSubtractOperation());
    }

    @Override
    protected String expectedResult() {
        setSubtract(true);

        return super.expectedResult();
    }
}