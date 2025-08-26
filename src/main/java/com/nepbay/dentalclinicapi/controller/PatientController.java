package com.nepbay.dentalclinicapi.controller;

import com.nepbay.dentalclinicapi.model.Patient;
import com.nepbay.dentalclinicapi.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Patient Controller
 * REST API endpoints for Patient operations
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    /**
     * GET /api/patients - Get all patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = patientService.getAllPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/patients/{id} - Get patient by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            Patient patient = patientService.getPatientById(id);
            if (patient != null) {
                return ResponseEntity.ok(patient);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /api/patients - Create new patient
     */
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody Patient patient) {
        try {
            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not create patient");
        }
    }
    
    /**
     * PUT /api/patients/{id} - Update patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not update patient");
        }
    }
    
    /**
     * DELETE /api/patients/{id} - Delete patient
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Patient deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not delete patient");
        }
    }
    
    /**
     * GET /api/patients/search?name={name} - Search patients by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String name) {
        try {
            List<Patient> patients = patientService.searchPatientsByName(name);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/patients/count - Get total patient count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getPatientCount() {
        try {
            Long count = patientService.getTotalPatientCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/patients/recent - Get recent patients
     */
    @GetMapping("/recent")
    public ResponseEntity<List<Patient>> getRecentPatients() {
        try {
            List<Patient> patients = patientService.getRecentPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}