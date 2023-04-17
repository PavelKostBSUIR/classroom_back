package com.softarex.classroom.api.service;

import com.softarex.classroom.api.dto.CreateMemberDto;
import com.softarex.classroom.api.dto.GetMemberDto;
import com.softarex.classroom.api.entity.Member;
import com.softarex.classroom.api.mapper.MemberMapper;
import com.softarex.classroom.api.repo.MemberRepository;
import com.softarex.classroom.api.repo.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public void toggleHand(String name) {
        Member member = memberRepository.findByName(name).orElseThrow(() -> new BadCredentialsException("user not found"));
        member.setHandRaised(!member.getHandRaised());
        memberRepository.save(member);
        log.info(String.format("Member \"%s\" toggled hand.", name));
    }

    @Transactional
    public GetMemberDto getByName(String name) {
        return memberMapper.memberToGetMemberDto(memberRepository.findByName(name).orElseThrow(RuntimeException::new));
    }

    @Transactional
    public List<GetMemberDto> getAll() {
        return memberRepository.findAll().stream().filter((member -> tokenRepository.findByMember_IdAndRevokedFalseAndExpiredFalse(member.getId()).size() != 0)).
                map(memberMapper::memberToGetMemberDto).collect(Collectors.toList());
    }
}
