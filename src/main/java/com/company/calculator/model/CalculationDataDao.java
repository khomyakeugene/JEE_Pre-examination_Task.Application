package com.company.calculator.model;

/**
 * Created by Yevhen on 16.05.2016.
 */
public interface CalculationDataDao {
    int storeCalculationData(int eventId, String expression, String result, long executionTime);

    int storeCalculationSuccess(String expression, String result, long executionTime);

    int storeCalculationError(String expression, String result, long executionTime);

}
