package com.softarex.classroom.api.controller.rest;

import com.softarex.classroom.api.dto.CreateMemberDto;
import com.softarex.classroom.api.dto.GetMemberDto;
import com.softarex.classroom.api.dto.LoginDto;
import com.softarex.classroom.api.dto.ToggleHandDto;
import com.softarex.classroom.api.service.MemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberRestController {

    public static final String MEMBERS_ENDPOINT = "api/members";
    public static final String MEMBER_ENDPOINT = "api/member";
    public static final String FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT = "classroom.members.toggle.hand.event";

    private final MemberService memberService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PatchMapping(MEMBER_ENDPOINT)
    public ToggleHandDto toggleHand(Principal principal) {
        memberService.toggleHand(principal.getName());
        simpMessagingTemplate.convertAndSend("/topic/" + FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT, ToggleHandDto.builder().message("MEMBER_HAND_TOGGLED").build());
        return ToggleHandDto.builder().message("MEMBER_HAND_TOGGLED").build();
    }

    @GetMapping(MEMBERS_ENDPOINT)
    public List<GetMemberDto> getAll() {

        return memberService.getAll();
    }

    @GetMapping(MEMBER_ENDPOINT)
    public GetMemberDto get(Principal principal) {
        return memberService.getByName(principal.getName());
    }
}
