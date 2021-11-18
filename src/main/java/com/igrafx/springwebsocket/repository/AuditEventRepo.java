package com.igrafx.springwebsocket.repository;

import com.igrafx.springwebsocket.models.AuditEvent;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditEventRepo extends MongoRepository<AuditEvent, String> {

  public AuditEvent findById(String id);
  public List<AuditEvent> findByName(String name);
}
