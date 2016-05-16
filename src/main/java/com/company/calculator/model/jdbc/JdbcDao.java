package com.company.calculator.model.jdbc;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class JdbcDao {
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
