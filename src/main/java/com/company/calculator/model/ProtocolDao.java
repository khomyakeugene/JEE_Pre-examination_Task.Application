package com.company.calculator.model;

/**
 * Created by Yevhen on 16.05.2016.
 */
public interface ProtocolDao {
    int insertRecord(int userId, int eventId, String description);

    int insertRecord(int eventId, String description);

    int insertRecord(int eventId);

    int storeConnectEvent();

    int storeDisconnectEvent();
}
