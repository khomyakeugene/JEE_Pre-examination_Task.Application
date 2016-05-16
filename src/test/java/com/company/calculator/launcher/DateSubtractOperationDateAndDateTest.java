package com.company.calculator.launcher;

import com.company.util.Util;
import org.junit.BeforeClass;

import java.util.Date;

/**
 * Created by Yevhen on 25.04.2016.
 */
public class DateSubtractOperationDateAndDateTest extends DateOperationTest {
    private Date firstOperand;
    private Date secondOperand;

    @BeforeClass
    public static void setUpClass() throws Exception {
        setOperation(new DateSubtractOperation());
    }

    @Override
    protected void initOperands() {
        firstOperand = com.company.calculator.launcher.Util.generateDate();
        secondOperand = com.company.calculator.launcher.Util.generateDate();

        operands.add(simpleDateFormat.format(firstOperand));
        operands.add(simpleDateFormat.format(secondOperand));
    }

    @Override
    protected String expectedResult() {
        ((DateOperation)getOperation()).initOperationData(getOperands());

        return Long.toString(Util.dateSub(firstOperand, secondOperand));
    }
}