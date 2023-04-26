package com.softarex.classroom.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
public class LoginDto {
    private final String message;
}
