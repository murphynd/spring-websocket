package com.igrafx.springwebsocket.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private  String id;
    private UUID uuid;
    private  String eventCategory;
    private  String eventType;
    private  String principal;
    private  String timestamp;
    private  String hostname;
    private  String platformVersion;
    private  Object eventData;
}
