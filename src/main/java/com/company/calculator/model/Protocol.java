package com.company.calculator.model;

import java.sql.Time;

/**
 * Created by Yevhen on 16.05.2016.
 */
public class Protocol {
    private int protocolId;
    private int userId;
    private int eventId;
    private Time eventDateTime;
    private String description;

    public int getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Time getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Time eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
