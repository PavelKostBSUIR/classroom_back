package com.softarex.classroom.api.service;

import com.softarex.classroom.api.dto.LoginDto;
import com.softarex.classroom.api.dto.LogoutDto;
import com.softarex.classroom.api.repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutService implements LogoutHandler {

    TokenRepository tokenRepository;
    SimpMessagingTemplate simpMessagingTemplate;
    public static final String FETCH_CLASSROOM_MEMBERS_LOGOUT_EVENT = "classroom.members.logout.event";

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            simpMessagingTemplate.convertAndSend("/topic/" + FETCH_CLASSROOM_MEMBERS_LOGOUT_EVENT, LogoutDto.builder().message("USER_LOGOUT").build());
        }
    }
}
