package com.softarex.classroom.api.controller.rest;

import com.softarex.classroom.api.domen.AuthenticationResponse;
import com.softarex.classroom.api.domen.RegisterRequest;
import com.softarex.classroom.api.dto.LoginDto;
import com.softarex.classroom.api.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService service;
    SimpMessagingTemplate simpMessagingTemplate;

    public static final String FETCH_CLASSROOM_MEMBERS_LOGIN_EVENT = "classroom.members.login.event";

    @PostMapping("/register")
    public AuthenticationResponse register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse authenticationResponse = service.register(request);
        simpMessagingTemplate.convertAndSend("/topic/" + FETCH_CLASSROOM_MEMBERS_LOGIN_EVENT, LoginDto.builder().message("USER_LOGIN").build());
        return authenticationResponse;
    }
}
