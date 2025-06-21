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
public class SessionStatus {
    @Id
    private Long sessionStatusId;

    @Column
    private String name;

    public SessionStatus(Long id) {
        this.sessionStatusId = id;
    }
}
