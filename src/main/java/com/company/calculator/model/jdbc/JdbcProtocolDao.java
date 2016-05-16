package com.company.calculator.model.jdbc;

import com.company.calculator.model.ProtocolDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcProtocolDao extends JdbcDao implements ProtocolDao {
    private static final String CANNOT_INSERT_RECORD_TO_PROTOCOL_PATTERN =
            "Cannot insert record to protocol (user_id = %d, event_id = %d, description = %s)";

    private static final String SQL_INSERT_QUERY =
            "INSERT INTO protocol (user_id, event_id, description) VALUES (?, ?, ?)";

    @Override
    public int insert(int userId, int eventId, String description) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(1, eventId);
            preparedStatement.setString(1, description);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else  {
                throw new RuntimeException(String.format(CANNOT_INSERT_RECORD_TO_PROTOCOL_PATTERN,
                        userId, eventId, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}