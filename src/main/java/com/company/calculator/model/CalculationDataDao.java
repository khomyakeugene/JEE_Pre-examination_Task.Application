package com.company.calculator.model;

/**
 * Created by Yevhen on 16.05.2016.
 */
public interface CalculationDataDao {
    int storeCalculationData(String expression, String result, long executionTime);
}
