package com.softarex.classroom.api.mapper;

import com.softarex.classroom.api.dto.CreateMemberDto;
import com.softarex.classroom.api.dto.GetMemberDto;
import com.softarex.classroom.api.entity.Member;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MemberMapper {
    Member getMemberDtoToMember(GetMemberDto getMemberDto);

    GetMemberDto memberToGetMemberDto(Member member);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Member updateMemberFromGetMemberDto(GetMemberDto getMemberDto, @MappingTarget Member member);

    Member createMemberDtoToMember(CreateMemberDto createMemberDto);

    CreateMemberDto memberToCreateMemberDto(Member member);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Member updateMemberFromCreateMemberDto(CreateMemberDto createMemberDto, @MappingTarget Member member);
}
