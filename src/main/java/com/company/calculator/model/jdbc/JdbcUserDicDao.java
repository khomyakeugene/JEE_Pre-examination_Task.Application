package com.company.calculator.model.jdbc;

import com.company.calculator.model.UserDicDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcUserDicDao extends JdbcDao implements UserDicDao {
    private static final String CANNOT_GET_USER_ID_PATTERN =
            "Cannot get <user_id> for <user_name> \"%s\"";
    private static final String SQL_GET_USED_ID_QUERY =
            "SELECT user_id FROM user_dic WHERE (user_name = ?)";

    private int currentUserId;

    @Override
    public int getUserIdByName(String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USED_ID_QUERY)) {

            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else  {
                throw new RuntimeException(String.format(CANNOT_GET_USER_ID_PATTERN, userName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCurrentUserId() {
        if (currentUserId == 0) {
            currentUserId = getUserIdByName(currentUser());
        }

        return currentUserId;
    }
}
