package com.abel.messagingapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration //to indicate that it is a Spring configuration class.
@EnableWebSocketMessageBroker //enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // Enables a simple memory based message broker to carry messages whose destinations
        // start with /topic to the message broker (broadcasting to other connected clients).
        config.enableSimpleBroker("/topic", "/queue");

        // Messages whose destinations start with /app are routed to
        // message-handling methods (applications work/controller)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // withSockJs helps in falling back to the next available communication protocol when
        // the browser doesn't support web socket
        registry.addEndpoint("/gs-guide-websocket").setHandshakeHandler(new RandomUsernameHandshakeHandler()).withSockJS();
    }

    private class RandomUsernameHandshakeHandler extends DefaultHandshakeHandler {

        private String usernames [] = {"abel", "kebede", "abebe", "alemu"};
        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

            String username = usernames[(int) (Math.random() * usernames.length)];
            return new UsernamePasswordAuthenticationToken(username, null);
        }
    }

}
