package com.softarex.classroom.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class GetMemberDto implements Serializable {
    private final Long id;
    private final String name;
    private final Boolean handRaised;
}
