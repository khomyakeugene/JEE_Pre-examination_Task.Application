package com.company.calculator.controllers;

import com.company.calculator.model.CalculationDataDao;
import com.company.calculator.model.ProtocolDao;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by Yevhen on 17.05.2016.
 */
public class CalculationDataController {
    private PlatformTransactionManager txManager;
    private CalculationDataDao calculationDataDao;
    private ProtocolDao protocolDao;

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCalculationDataDao(CalculationDataDao calculationDataDao) {
        this.calculationDataDao = calculationDataDao;
    }

    public void setProtocolDao(ProtocolDao protocolDao) {
        this.protocolDao = protocolDao;
    }

    public int storeConnectEvent() {
        return protocolDao.storeConnectEvent();
    }

    public int storeDisconnectEvent() {
        return protocolDao.storeDisconnectEvent();
    }

    public int storeCalculationSuccess(String expression, String result, Long executionTime) {
        return calculationDataDao.storeCalculationSuccess(expression, result, executionTime);
    }

    public int storeCalculationError(String expression, String result, Long executionTime) {
        return calculationDataDao.storeCalculationError(expression, result, executionTime);
    }
}
