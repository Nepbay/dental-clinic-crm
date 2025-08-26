package com.nepbay.dentalclinicapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Root Controller
 * Handles root endpoints and API connectivity testing
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class RootController {
    
    /**
     * GET / - Root endpoint with welcome message
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🦷 Welcome to Dental Clinic API");
        response.put("version", "1.0.0");
        response.put("status", "READY");
        response.put("author", "Nepbay");
        response.put("endpoints", Map.of(
            "patients", "/api/patients",
            "appointments", "/api/appointments", 
            "dashboard", "/api/dashboard",
            "health", "/actuator/health",
            "database", "/h2-console"
        ));
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/test - API connectivity test endpoint
     */
    @GetMapping("/api/test")
    public ResponseEntity<Map<String, Object>> apiTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "✅ API is working correctly!");
        response.put("timestamp", java.time.Instant.now());
        response.put("status", "OK");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}