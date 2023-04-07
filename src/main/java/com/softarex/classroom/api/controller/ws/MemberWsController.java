package com.softarex.classroom.api.controller.ws;

import com.softarex.classroom.api.dto.ToggleHandDto;
import com.softarex.classroom.api.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class MemberWsController {

    public static final String CLASSROOM_MEMBER_TOGGLE_HAND = "classroom.members.{member_id}.toggle_hand";
    public static final String FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT = "classroom.members.toggle_hand.event";
    MemberService memberService;

    @MessageMapping("/" + CLASSROOM_MEMBER_TOGGLE_HAND)
    @SendTo("/topic/" + FETCH_CLASSROOM_MEMBERS_TOGGLE_HAND_EVENT)
    //@PreAuthorize("principal.username == memberService.getByName(memberId)")
    public ToggleHandDto classroomMemberToggleHand(@DestinationVariable("member_id") Long memberId) {
        memberService.toggleHand(memberId);
        return ToggleHandDto.builder().build();
    }

}
