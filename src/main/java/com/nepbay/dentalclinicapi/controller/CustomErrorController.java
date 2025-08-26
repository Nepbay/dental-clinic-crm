package com.nepbay.dentalclinicapi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Error Controller
 * Provides custom error responses instead of whitelabel error page
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@RestController
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        Object message = request.getAttribute("jakarta.servlet.error.message");
        Object path = request.getAttribute("jakarta.servlet.error.request_uri");
        
        int statusCode = status != null ? (Integer) status : 500;
        
        response.put("error", true);
        response.put("status", statusCode);
        String errorMessage = message != null && !message.toString().isEmpty() ? 
                             message.toString() : getErrorMessage(statusCode);
        response.put("message", errorMessage);
        response.put("path", path != null ? path.toString() : "unknown");
        response.put("timestamp", java.time.Instant.now());
        response.put("api", "Dental Clinic API v1.0.0");
        
        return ResponseEntity.status(statusCode).body(response);
    }
    
    private String getErrorMessage(int statusCode) {
        return switch (statusCode) {
            case 404 -> "🔍 Endpoint not found - Check API documentation at /";
            case 400 -> "❌ Bad request - Please check your request parameters";
            case 401 -> "🔐 Unauthorized - Authentication required";
            case 403 -> "⛔ Forbidden - Access denied";
            case 500 -> "🛠️ Internal server error - Please try again later";
            default -> "❓ An error occurred";
        };
    }
}