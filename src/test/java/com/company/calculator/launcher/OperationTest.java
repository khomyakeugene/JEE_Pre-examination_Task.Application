package com.company.calculator.launcher;

import com.company.calculator.library.Operation;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yevhen on 24.04.2016.
 */
public abstract class OperationTest {
    private static Operation operation;
    ArrayList<String> operands = new ArrayList<>();

    protected static void setOperation(Operation operation) {
        OperationTest.operation = operation;
    }

    static Operation getOperation() {
        return operation;
    }

    ArrayList<String> getOperands() {
        return operands;
    }

    protected abstract void initOperands();

    protected abstract String expectedResult();

    @org.junit.Test(timeout = 1000)
    public void executeTest() throws Exception {
        initOperands();
        operation.setOperands(operands);

        String s1 = expectedResult();
        String s2 = operation.execute();

        assertEquals(expectedResult(), operation.execute());
    }
}
