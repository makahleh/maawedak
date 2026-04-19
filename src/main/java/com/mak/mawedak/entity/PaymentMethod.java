package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    @EqualsAndHashCode.Include
    private Long paymentMethodId;

    @Column(nullable = false, unique = true)
    private String name;

    public PaymentMethod(Long id) {
        this.paymentMethodId = id;
    }
}
