package com.softarex.classroom.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    public static final String REGISTRY = "/ws";
    public static final String TOPIC_DESTINATION_PREFIX = "/topic/";
    public static final String APP_DESTINATION_PREFIX = "/app/";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(REGISTRY)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC_DESTINATION_PREFIX);
        config.setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX);
    }
}
