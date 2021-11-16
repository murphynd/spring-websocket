package com.igrafx.springwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping
    @SendTo("/topic/greetings")
    public Greeting greet(Message message){
        return new Greeting("hello,"+ HtmlUtils.htmlEscape(message.getName()));
    }
}
