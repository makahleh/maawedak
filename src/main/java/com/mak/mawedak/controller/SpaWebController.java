package com.mak.mawedak.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SpaWebController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) {
        // Get the requested URI that caused the 404
        String requestUri = (String) request.getAttribute(jakarta.servlet.RequestDispatcher.FORWARD_REQUEST_URI);

        // If it's an API request that failed, we probably shouldn't return index.html
        if (requestUri != null && requestUri.startsWith("/api/")) {
            return null; // Let standard error handling deal with missing API routes
        }

        // Ignore noisy requests like Chrome DevTools looking for .well-known
        // configurations
        if (requestUri != null && requestUri.startsWith("/.well-known/")) {
            response.setStatus(404);
            return null;
        }

        // For all other 404s (SPA routes), forward to the static index.html
        return "forward:/index.html";
    }
}
