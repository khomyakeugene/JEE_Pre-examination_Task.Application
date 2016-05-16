package com.company.calculator.launcher;

import com.company.util.Util;

import java.util.Date;

/**
 * Created by Yevhen on 25.04.2016.
 */
public class DateOperationDateAndIntegerTest extends DateOperationTest {
    private Date firstOperand;
    private Integer secondOperand;
    private boolean subtract;

    void setSubtract(boolean subtract) {
        this.subtract = subtract;
    }

    @Override
    protected void initOperands() {
        firstOperand = com.company.calculator.launcher.Util.generateDate();
        secondOperand = com.company.calculator.launcher.Util.getRandomInteger();

        operands.add(simpleDateFormat.format(firstOperand));
        operands.add(secondOperand.toString());
    }

    @Override
    protected String expectedResult() {
        ((DateOperation)getOperation()).initOperationData(getOperands());

        return simpleDateFormat.format(Util.dateAdd(firstOperand, (subtract ? -1 : 1) * secondOperand));
    }
}