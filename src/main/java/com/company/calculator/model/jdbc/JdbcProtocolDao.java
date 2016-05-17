package com.company.calculator.model.jdbc;

import com.company.calculator.model.ProtocolDao;

import java.sql.*;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcProtocolDao extends JdbcDao implements ProtocolDao {
    private static final String CANNOT_GET_LAST_GENERATED_PROTOCOL_ID_PATTERN =
            "Cannot get last generated <protocol.protocol_id> (user_id = %d, event_id = %d, description = \"%s\")";
    private static final String SQL_INSERT_QUERY =
            "INSERT INTO protocol (user_id, event_id, description) VALUES (?, ?, ?)";

    private int currentUserId;

    public void setJdbcUserDicDao(JdbcUserDicDao jdbcUserDicDao) {
        this.jdbcUserDicDao = jdbcUserDicDao;
    }

    private JdbcUserDicDao jdbcUserDicDao;

    private int getCurrentUserId() {
        if (currentUserId == 0) {
            currentUserId = jdbcUserDicDao.getCurrentUserId();
        }

        return currentUserId;
    }

    @Override
    public int insert(int userId, int eventId, String description) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, eventId);
            preparedStatement.setString(3, description);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else  {
                throw new RuntimeException(String.format(CANNOT_GET_LAST_GENERATED_PROTOCOL_ID_PATTERN,
                        userId, eventId, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(int eventId, String description) {
        return insert(getCurrentUserId(), eventId, description);
    }
}
