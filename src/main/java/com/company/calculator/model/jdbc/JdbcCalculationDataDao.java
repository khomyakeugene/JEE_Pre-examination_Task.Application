package com.company.calculator.model.jdbc;

import com.company.calculator.model.CalculationDataDao;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcCalculationDataDao extends JdbcDao implements CalculationDataDao {
    @Override
    public int storeCalculationData(String expression, String result, long executionTime) {
        return 0;
    }
}
