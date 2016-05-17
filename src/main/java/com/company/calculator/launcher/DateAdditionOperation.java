package com.company.calculator.launcher;

import com.company.calculator.library.Operation;
import com.company.calculator.library.OperatorType;
import com.company.util.Util;

import java.util.Date;
import java.util.List;

/**
 * Created by Yevhen on 24.04.2016.
 */
public class DateAdditionOperation extends DateOperation implements Operation {
    private Date dateOperand;
    private Integer daysOperand;

    @Override
    public boolean initOperationData(List<String> operandList) {
        boolean result = super.initOperationData(operandList);

        if (result) {
            // It is possible only to dateAdd Date and Integer(days count)
            // Also initialize <dateOperand> and <daysOperand> using further in the method <execute>
            if (firstOperandDateRepresentation != null) {
                result = secondOperandIntegerRepresentation != null;
                if (result) {
                    dateOperand = firstOperandDateRepresentation;
                    daysOperand = secondOperandIntegerRepresentation;
                }
            } else {
                // In this case it is guaranteed by <super.isThisOperation> that <secondOperandDateRepresentation>
                // and <secondOperandIntegerRepresentation> are correctly initialized
                dateOperand = secondOperandDateRepresentation;
                daysOperand = firstOperandIntegerRepresentation;
            }
        }

        return result;
    }

    @Override
    protected Date calculate() {
        return  Util.dateAdd(dateOperand, daysOperand);
    }

    @Override
    public OperatorType operatorType() {
        return OperatorType.BINARY;
    }
}
