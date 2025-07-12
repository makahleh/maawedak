package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String role;
    private SystemSettingsDTO systemSettings;
}
