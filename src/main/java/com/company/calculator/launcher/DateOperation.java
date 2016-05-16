package com.company.calculator.launcher;

import com.company.calculator.library.BinaryEmptyOperation;
import com.company.calculator.library.Operation;
import com.company.calculator.library.ParseResult;
import com.company.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Yevhen on 24.04.2016.
 */
public abstract class DateOperation extends BinaryEmptyOperation implements Operation {
    private static final String[] USEFUL_DATE_PATTERNS = new String[] {
            "yyyy-MM-dd", "dd/MM/yyyy", "dd/MM/yy", "dd.MM.yyyy", "dd.MM.yy", "dd-MM-yyyy", "dd-MM-yy"
    };

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private String usefulDatePattern;
    protected Date firstOperandDateRepresentation;
    protected Date secondOperandDateRepresentation;
    protected Integer firstOperandIntegerRepresentation;
    protected Integer secondOperandIntegerRepresentation;

    public boolean initOperationData(List<String> operandList) {
        boolean result;

        // Try to recognise date-signature in the operands
        String firstOperand = operandList.get(0);
        String secondOperand = operandList.get(1);

        firstOperandDateRepresentation = convertToDate(firstOperand);
        secondOperandDateRepresentation = convertToDate(secondOperand);
        result = (firstOperandDateRepresentation != null) || (secondOperandDateRepresentation  != null);
        if (result) {
            // One of the operands can be Integer
            if (firstOperandDateRepresentation == null) {
                // Try to recognize first operand as Integer
                firstOperandIntegerRepresentation = Util.parseInt(firstOperand);
                // Only Integer could be one of the not-date operands in date-operation
                result = firstOperandIntegerRepresentation != null;
            }
            if (secondOperandDateRepresentation == null) {
                // Try to recognize second operand as Integer
                secondOperandIntegerRepresentation = Util.parseInt(secondOperand);
                // Only Integer could be one of the not-date operands in date-operation
                result = secondOperandIntegerRepresentation != null;
            }
        }

        return result;
    }

    @Override
    public boolean isThisOperation(String inputExpression, ParseResult parseResult) {
        // Also check whether there are only two operands
        return super.isThisOperation(inputExpression, parseResult) &&
                initOperationData(parseResult.operandList());
    }

    private Date convertToDate(String data) {
        Date result = null;

        for (String datePattern : USEFUL_DATE_PATTERNS) {
            simpleDateFormat.applyPattern(datePattern);
            try {
                result = simpleDateFormat.parse(data);
                if (result != null) {
                    // Fix useful date-pattern
                    usefulDatePattern = datePattern;
                    break;
                }
            } catch (ParseException e) {
            }
        }

        return result;
    }

    // Should be overridden in the descendant class
    protected Date calculate() {
        return null;
    }

    @Override
    public String execute() {
        return dateStringRepresentation(calculate());
    }

    protected String dateStringRepresentation(Date date) {
        simpleDateFormat.applyPattern(usefulDatePattern);

        return simpleDateFormat.format(date);
    }
}
