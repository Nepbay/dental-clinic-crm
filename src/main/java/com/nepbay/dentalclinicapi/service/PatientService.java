package com.nepbay.dentalclinicapi.service;

import com.nepbay.dentalclinicapi.model.Patient;
import com.nepbay.dentalclinicapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Patient Service
 * Business logic for Patient operations
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    /**
     * Get patient by ID
     */
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }
    
    /**
     * Create a new patient
     */
    public Patient createPatient(Patient patient) {
        // Validate phone number uniqueness
        if (patientRepository.existsByPhone(patient.getPhone())) {
            throw new RuntimeException("A patient with this phone number already exists");
        }
        
        // Validate email uniqueness (if provided)
        if (patient.getEmail() != null && !patient.getEmail().isEmpty() 
            && patientRepository.existsByEmail(patient.getEmail())) {
            throw new RuntimeException("A patient with this email already exists");
        }
        
        return patientRepository.save(patient);
    }
    
    /**
     * Update existing patient
     */
    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existingPatient = getPatientById(id);
        if (existingPatient == null) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }
        
        // Check phone uniqueness (if changed)
        if (!existingPatient.getPhone().equals(updatedPatient.getPhone()) 
            && patientRepository.existsByPhone(updatedPatient.getPhone())) {
            throw new RuntimeException("A patient with this phone number already exists");
        }
        
        // Check email uniqueness (if changed)
        if (updatedPatient.getEmail() != null && !updatedPatient.getEmail().isEmpty() 
            && !updatedPatient.getEmail().equals(existingPatient.getEmail())
            && patientRepository.existsByEmail(updatedPatient.getEmail())) {
            throw new RuntimeException("A patient with this email already exists");
        }
        
        // Update fields
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setAddress(updatedPatient.getAddress());
        
        return patientRepository.save(existingPatient);
    }
    
    /**
     * Delete patient
     */
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    /**
     * Search patients by name
     */
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Get patient statistics
     */
    public Long getTotalPatientCount() {
        return patientRepository.countTotalPatients();
    }
    
    public List<Patient> getPatientsRegisteredToday() {
        return patientRepository.findPatientsRegisteredToday();
    }
    
    public List<Patient> getPatientsRegisteredThisMonth() {
        return patientRepository.findPatientsRegisteredThisMonth();
    }
    
    public List<Patient> getRecentPatients() {
        return patientRepository.findRecentPatients();
    }
}