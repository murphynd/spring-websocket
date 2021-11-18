package com.igrafx.springwebsocket.models;

import org.springframework.data.annotation.Id;

public class AuditEvent {

    @Id
    private String id;
    private String name;

    public AuditEvent() {}

    public AuditEvent(String name) {
        this.setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("😀[id=%s, name='%s'']",getId(), getName());
    }
}
