package com.company.calculator.launcher;

import com.company.calculator.library.Operation;
import com.company.calculator.library.OperatorType;
import com.company.calculator.library.ParseResult;
import com.company.util.Util;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class DateSubtractOperation extends DateOperation implements Operation {
    @Override
    public boolean isThisOperation(String inputExpression, ParseResult parseResult) {
        return super.isThisOperation(inputExpression, parseResult) &&
                (firstOperandDateRepresentation != null);
    }

    @Override
    public String execute() {
        String result;

        // The first operand is always the date (<firstOperandDateRepresentation> is always not null,
        // see method <isThisOperation>)
        if (secondOperandDateRepresentation != null) {
            // Try to subtract two dates
            result = Long.toString(Util.dateSub(firstOperandDateRepresentation, secondOperandDateRepresentation));
        } else {
            // In this case it is guaranteed by <super.isThisOperation> that
            // <secondOperandIntegerRepresentation> are correctly initialized
            result = dateStringRepresentation(
                    Util.dateAdd(firstOperandDateRepresentation, -secondOperandIntegerRepresentation));
        }

        return result;
    }

    @Override
    public OperatorType operatorType() {
        return OperatorType.BINARY;
    }
}
