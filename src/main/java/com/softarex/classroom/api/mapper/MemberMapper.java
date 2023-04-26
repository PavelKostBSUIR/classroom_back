package com.softarex.classroom.api.mapper;

import com.softarex.classroom.api.dto.CreateMemberDto;
import com.softarex.classroom.api.dto.GetMemberDto;
import com.softarex.classroom.api.entity.Member;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MemberMapper {
    GetMemberDto memberToGetMemberDto(Member member);

    Member createMemberDtoToMember(CreateMemberDto createMemberDto);

}
