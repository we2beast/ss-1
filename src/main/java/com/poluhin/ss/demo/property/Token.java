package com.poluhin.ss.demo.property;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record Token(
    Long ttlInSeconds,
    String key
) {
}
