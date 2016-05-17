package com.company.calculator.model.jdbc;

import com.company.calculator.model.CalculationDataDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcCalculationDataDao extends JdbcDao implements CalculationDataDao {
    private static final int SUCCESSFUL_EXPRESSION_VALUE_CALCULATION_EVENT_ID = 4;
    private static final int ERROR_WHILE_TRYING_TO_CALCULATE_EXPRESSION_VALUE_EVENT_ID = 5;

    private static final String PROTOCOL_MESSAGE_PATTERN = "%s = %s";

    private JdbcProtocolDao jdbcProtocolDao;

    public void setJdbcProtocolDao(JdbcProtocolDao jdbcProtocolDao) {
        this.jdbcProtocolDao = jdbcProtocolDao;
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public int storeCalculationData(int eventId, String expression, String result, long executionTime) {
        // Store protocol event
        int protocolId = jdbcProtocolDao.insert(eventId, String.format(PROTOCOL_MESSAGE_PATTERN, expression, result));

        // Store calculation data

        return 0;
    }

    @Override
    public int storeCalculationSuccess(String expression, String result, long executionTime) {
        return storeCalculationData(SUCCESSFUL_EXPRESSION_VALUE_CALCULATION_EVENT_ID, expression, result, executionTime);
    }

    @Override
    public int storeCalculationError(String expression, String result, long executionTime) {
        return storeCalculationData(ERROR_WHILE_TRYING_TO_CALCULATE_EXPRESSION_VALUE_EVENT_ID, expression, result,
                executionTime);
    }
}
