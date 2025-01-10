package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    private Long paymentMethodId;

    @Column(nullable = false, unique = true)
    private String name;

    public PaymentMethod(Long id) {
        this.paymentMethodId = id;
    }
}
