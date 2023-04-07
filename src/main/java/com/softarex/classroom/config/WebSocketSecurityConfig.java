package com.softarex.classroom.config;

import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

//@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    //@Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
                .simpDestMatchers("/ws/**", "/topic/**").permitAll().
                anyMessage().authenticated();

        return messages.build();
    }
}