package com.softarex.classroom.api.repo;

import com.softarex.classroom.api.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("select t from Token t where t.member.id = ?1 and t.revoked = false and t.expired = false")
    List<Token> findByMember_IdAndRevokedFalseAndExpiredFalse(Long id);

    Optional<Token> findByToken(String token);

}