package com.company.calculator.model;

/**
 * Created by Yevhen on 16.05.2016.
 */
public interface ProtocolDao {
    int insert(int userId, int eventId, String description);
}
