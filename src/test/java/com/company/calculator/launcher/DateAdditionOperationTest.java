package com.company.calculator.launcher;

import org.junit.BeforeClass;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class DateAdditionOperationTest extends DateOperationDateAndIntegerTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new DateAdditionOperation());
    }

    @Override
    protected String expectedResult() {
        setSubtract(false);

        return super.expectedResult();
    }
}