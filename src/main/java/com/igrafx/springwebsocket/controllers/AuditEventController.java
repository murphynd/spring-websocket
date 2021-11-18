package com.igrafx.springwebsocket.controllers;

import com.igrafx.springwebsocket.models.AuditEvent;
import com.igrafx.springwebsocket.repository.AuditEventRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class AuditEventController  implements InitializingBean {

  @Autowired
  private SimpMessagingTemplate template;
  @Autowired
  private AuditEventRepo repository;

  /**
   * Sending a message directly from the websocket client.
   * @param auditEvent
   * @return
   * @throws Exception
   */
  @MessageMapping("/auditEvents/list")
  @SendTo("/topic/auditEvents/list")
  public List<AuditEvent> list() throws Exception {
    return repository.findAll();
  }

  /**
   * Web-Socket handler.
   * @param destination
   * @param auditEvent
   * @throws Exception
   */
  @MessageMapping("/auditEvents/{destination}")
  public void webSocketHandler(@DestinationVariable String destination, AuditEvent auditEvent) throws Exception {

    if (destination == null) {
      return;
    }

    if ("add".equals(destination)) {
      auditEvent = repository.save(auditEvent);
    } else if ("remove".equals(destination)) {
      System.out.println("REMOVING: " + auditEvent.getId());
      repository.delete(auditEvent.getId());
    }
    template.convertAndSend("/topic/auditEvents/" + destination, auditEvent);
  }

  /**
   * API Handler. Examples:
   * 	curl -F "eventcategory=loggin" -X POST "http://localhost/api/auditEvent/add"
   * 	curl -F "id={USER_ID}" -X POST "http://localhost/api/auditEvent/remove"
   * @param destination
   * @param auditEvent
   */
  @RequestMapping(value = "/api/users/{destination}", method = RequestMethod.POST)
  public void apiHandler(@PathVariable("destination") String destination, @ModelAttribute AuditEvent auditEvent) throws Exception {
    webSocketHandler(destination, auditEvent);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(template, "Template is null!");
    Assert.notNull(repository, "Repo is null!");
  }
}
