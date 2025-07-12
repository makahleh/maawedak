package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPersonalInfoDTO {
    private Long customerPersonalInfoId;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String address;
    private String taxNumber;
}
