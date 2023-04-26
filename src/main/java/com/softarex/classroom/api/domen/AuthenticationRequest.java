package com.softarex.classroom.api.domen;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
    private final String name;
}
