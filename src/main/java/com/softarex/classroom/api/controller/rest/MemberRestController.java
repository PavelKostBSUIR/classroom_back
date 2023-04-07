package com.softarex.classroom.api.controller.rest;

import com.softarex.classroom.api.dto.CreateMemberDto;
import com.softarex.classroom.api.dto.GetMemberDto;
import com.softarex.classroom.api.service.MemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(MemberRestController.MEMBERS_ENDPOINT)
public class MemberRestController {

    public static final String MEMBERS_ENDPOINT = "/api/members";
    public static final String MEMBER_ENDPOINT = "/member";
    MemberService memberService;

    @PostMapping
    public void create(@RequestBody @Valid CreateMemberDto createMemberDto) {
        memberService.create(createMemberDto);
    }

    @GetMapping
    public List<GetMemberDto> getAll() {

        return memberService.getAll();
    }

    @GetMapping(MEMBER_ENDPOINT)
    public GetMemberDto getByName(@RequestParam("name") String name) {
        return memberService.getByName(name);
    }
}
