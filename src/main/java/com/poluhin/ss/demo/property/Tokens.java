package com.poluhin.ss.demo.property;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record Tokens(
    Token access,
    Token refresh
) {
}

