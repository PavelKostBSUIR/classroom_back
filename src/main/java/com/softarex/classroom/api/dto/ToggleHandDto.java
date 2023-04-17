package com.softarex.classroom.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
public class ToggleHandDto {
    private final String message;
}
