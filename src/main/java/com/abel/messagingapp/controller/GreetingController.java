package com.abel.messagingapp.controller;

import com.abel.messagingapp.model.Greeting;
import com.abel.messagingapp.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

/**
 *
 * STOMP messages can be routed to @Controller classes.
 * The GreetingController is mapped to handle messages to the /hello destination
 *
 */
@Controller
public class GreetingController {

    /**
     * The @MessageMapping annotation ensures that, if a message is sent to the /hello destination,
     * the greeting() method is called.
     *
     * The return value is broadcast to all subscribers of /topic/greetings,
     * as specified in the @SendTo annotation.
     *
     * Note that the name from the input message is sanitized, since, in this case,
     * it will be echoed back and re-rendered in the browser DOM on the client side.
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings/1")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {

        return new Greeting(principal.getName() + ": Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
