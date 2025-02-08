package com.mak.mawedak.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextHolderHelper {
    public static Long getCustomerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = (Claims) authentication.getPrincipal();
        return claims.get("customerId", Long.class);
    }
}
