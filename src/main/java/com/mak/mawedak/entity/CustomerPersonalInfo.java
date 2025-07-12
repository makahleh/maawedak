package com.mak.mawedak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPersonalInfoId;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column
    private String taxNumber;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    public CustomerPersonalInfo(Long customerPersonalInfoId) {
        this.customerPersonalInfoId = customerPersonalInfoId;
    }
}
