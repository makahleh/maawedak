package com.mak.mawedak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionMethod {
    @Id
    private Long subscriptionMethodId;

    @Column(nullable = false, unique = true)
    private String name;

    public SubscriptionMethod(Long id) {
        this.subscriptionMethodId = id;
    }
}
