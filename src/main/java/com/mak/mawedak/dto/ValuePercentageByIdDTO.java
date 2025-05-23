package com.mak.mawedak.dto;

import java.math.BigDecimal;

public record ValuePercentageByIdDTO(
        Object id,
        BigDecimal value,
        double percentage
) {
}
