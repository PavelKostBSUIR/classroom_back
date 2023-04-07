package com.softarex.classroom.api.service;

import com.softarex.classroom.api.domen.AuthenticationRequest;
import com.softarex.classroom.api.domen.AuthenticationResponse;
import com.softarex.classroom.api.domen.RegisterRequest;
import com.softarex.classroom.api.entity.Member;
import com.softarex.classroom.api.entity.Token;
import com.softarex.classroom.api.repo.MemberRepository;
import com.softarex.classroom.api.repo.TokenRepository;
import com.softarex.classroom.enum_.Role;
import com.softarex.classroom.enum_.TokenType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    MemberRepository repository;
    TokenRepository tokenRepository;
    JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        var member = Member.builder().name(request.getName())
                .role(Role.USER)
                .build();
        var oldMember = repository.findByName(member.getName());
        if (oldMember.isEmpty()) {
            var savedMember = repository.save(member);
            var jwtToken = jwtService.generateToken(savedMember);
            saveUserToken(savedMember, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            if (tokenRepository.findByMember_IdAndRevokedFalseAndExpiredFalse(oldMember.get().getId()).isEmpty()) {
                return authenticate(AuthenticationRequest.builder().name(request.getName()).build());
            } else {
                throw new BadCredentialsException("Such user is existed");
            }
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByName(request.getName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(Member user, String jwtToken) {
        var token = Token.builder()
                .member(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Member user) {
        var validUserTokens = tokenRepository.findByMember_IdAndRevokedFalseAndExpiredFalse(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
