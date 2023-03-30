package com.softarex.classroom.api.controller.ws;

import com.softarex.classroom.api.dto.ToggleHandDto;
import com.softarex.classroom.api.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class MemberWsController {

    public static final String CLASSROOM_MEMBER_TOGGLE_HAND = "classroom.members.{member_id}.toggle_hand";
    public static final String FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT = "classroom.members.toggle_hand.event";
    MemberService memberService;
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping(CLASSROOM_MEMBER_TOGGLE_HAND)
    public void classroomMemberToggleHand(@DestinationVariable("member_id") Long memberId) {
        memberService.toggleHand(memberId);
        messagingTemplate.convertAndSend(FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT, ToggleHandDto.builder().build());
    }

    @SubscribeMapping(FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT)
    public ToggleHandDto fetchClassroomMembersToggleHandEvent() {
        return null;
    }

}
