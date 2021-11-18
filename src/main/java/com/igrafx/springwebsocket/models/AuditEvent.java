package com.igrafx.springwebsocket.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class AuditEvent {

    @Id
    private String id;
    private UUID uuid;
    private String eventCategory;
    private String eventType;
    private String principal;
    private String hostname;
    private String platformVersion;
    private String eventData;
    private Date timestamp;

    public AuditEvent() {}

    public AuditEvent(String id,UUID uuid,String eventCategory,String eventType,String principal,String hostname,String platformVersion,String eventData,Date timestamp) {
        this.id=id;
        this.uuid = uuid;
        this.eventCategory = eventCategory;
        this.eventType= eventType;
        this.principal= principal;
        this.hostname= hostname;
        this.platformVersion= platformVersion;
        this.eventData= eventData;
        this.timestamp= timestamp;
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        return String.format("😀[id=%s, hostname='%s', EventCategory=%s']",getId(), getHostname(),getEventCategory());
    }

    public UUID getUuid() {
        return uuid;
    }



    public String getEventCategory() {
        return eventCategory;
    }



    public String getEventType() {
        return eventType;
    }



    public String getPrincipal() {
        return principal;
    }


    public String getHostname() {
        return hostname;
    }



    public String getPlatformVersion() {
        return platformVersion;
    }



    public String getEventData() {
        return eventData;
    }


    public Date getTimestamp() {
        return timestamp;
    }








}
