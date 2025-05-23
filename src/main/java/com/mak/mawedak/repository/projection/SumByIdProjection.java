package com.mak.mawedak.repository.projection;

import java.math.BigDecimal;

public interface SumByIdProjection {
    Long getId();

    BigDecimal getTotal();
}
