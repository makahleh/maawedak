package com.mak.mawedak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class SubscriptionMethod {
    @Id
    @EqualsAndHashCode.Include
    private Long subscriptionMethodId;

    @Column(nullable = false, unique = true)
    private String name;

    public SubscriptionMethod(Long id) {
        this.subscriptionMethodId = id;
    }
}
