package com.igrafx.springwebsocket.repository;

import com.igrafx.springwebsocket.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepo extends MongoRepository<Message, String> {
}
