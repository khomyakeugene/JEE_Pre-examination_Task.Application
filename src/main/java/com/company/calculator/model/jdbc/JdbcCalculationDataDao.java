package com.company.calculator.model.jdbc;

import com.company.calculator.model.CalculationDataDao;
import com.company.calculator.model.ProtocolDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcCalculationDataDao extends JdbcDao implements CalculationDataDao {
    private static final int SUCCESSFUL_EXPRESSION_VALUE_CALCULATION_EVENT_ID = 4;
    private static final int ERROR_WHILE_TRYING_TO_CALCULATE_EXPRESSION_VALUE_EVENT_ID = 5;

    private static final String CANNOT_GET_LAST_GENERATED_CALCULATION_DATA_ID_PATTERN =
            "Cannot get last generated <calculation_data.calculation_data_id> (protocol_id = %d, expression = \"%s\", " +
                    "result = \"%s\", microsecs_execution_time = %d)";
    private static final String SQL_INSERT_QUERY =
            "INSERT INTO calculation_data (protocol_id, expression, result, microsecs_execution_time) VALUES (?, ?, ?, ?)";

    private static final String PROTOCOL_MESSAGE_PATTERN = "%s = %s";

    private ProtocolDao protocolDao;

    public void setProtocolDao(JdbcProtocolDao protocolDao) {
        this.protocolDao = protocolDao;
    }

    private int storeProtocolRecord(int eventId, String expression, String result) {
        return protocolDao.insertRecord(eventId, String.format(PROTOCOL_MESSAGE_PATTERN, expression, result));
    }

    private int storeCalculationDataRecord(int protocolId, String expression, String result, Long executionTime) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.clearParameters();
            preparedStatement.setInt(1, protocolId);
            preparedStatement.setString(2, expression);
            preparedStatement.setString(3, result);
            if (executionTime != null) {
                preparedStatement.setLong(4, executionTime);
            }
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else  {
                throw new RuntimeException(String.format(CANNOT_GET_LAST_GENERATED_CALCULATION_DATA_ID_PATTERN,
                        protocolId, expression, result, executionTime));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public int storeCalculationData(int eventId, String expression, String result, Long executionTime) {
        return storeCalculationDataRecord(storeProtocolRecord(eventId, expression, result), expression, result,
                executionTime);
    }

    @Override
    public int storeCalculationSuccess(String expression, String result, Long executionTime) {
        return storeCalculationData(SUCCESSFUL_EXPRESSION_VALUE_CALCULATION_EVENT_ID, expression, result, executionTime);
    }

    @Override
    public int storeCalculationError(String expression, String result, Long executionTime) {
        return storeCalculationData(ERROR_WHILE_TRYING_TO_CALCULATE_EXPRESSION_VALUE_EVENT_ID, expression, result,
                executionTime);
    }
}
