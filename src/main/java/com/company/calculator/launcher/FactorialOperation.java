package com.company.calculator.launcher;

import com.company.calculator.library.Operation;
import com.company.calculator.library.OperatorType;
import com.company.calculator.library.ParseResult;
import com.company.calculator.library.UnaryIntegerOperation;
import com.company.util.Factorial;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class FactorialOperation extends UnaryIntegerOperation implements Operation {
    @Override
    protected String calculate() {
        return Factorial.factorial(getIntegerOperand(0)).toString();
    }

    @Override
    public boolean isThisOperation(String inputExpression, ParseResult parseResult) {
        return super.isThisOperation(inputExpression, parseResult) &&
                (parseResult.operatorType() == OperatorType.POSTFIX_UNARY);
    }
}
