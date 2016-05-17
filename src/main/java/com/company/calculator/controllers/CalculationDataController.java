package com.company.calculator.controllers;

import com.company.calculator.model.CalculationDataDao;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yevhen on 17.05.2016.
 */
public class CalculationDataController {
    private PlatformTransactionManager txManager;
    private CalculationDataDao calculationDataDao;

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCalculationDataDao(CalculationDataDao calculationDataDao) {
        this.calculationDataDao = calculationDataDao;
    }

    public int storeCalculationSuccess(String expression, String result, long executionTime) {
        return calculationDataDao.storeCalculationSuccess(expression, result, executionTime);
    }

    public int storeCalculationError(String expression, String result, long executionTime) {
        return calculationDataDao.storeCalculationError(expression, result, executionTime);
    }
}
