package com.company.calculator.model.jdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcDao {
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected String currentUser() {
        try {
            return dataSource.getConnection().getMetaData().getUserName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
