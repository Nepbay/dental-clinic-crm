package com.nepbay.dentalclinicapi.repository;

import com.nepbay.dentalclinicapi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Patient Repository
 * Handles database operations for Patient entity
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Find patient by phone number
    Optional<Patient> findByPhone(String phone);
    
    // Find patient by email
    Optional<Patient> findByEmail(String email);
    
    // Find patients by name (case-insensitive search)
    List<Patient> findByNameContainingIgnoreCase(String name);
    
    // Check if phone already exists
    boolean existsByPhone(String phone);
    
    // Check if email already exists
    boolean existsByEmail(String email);
    
    // Count total patients
    @Query("SELECT COUNT(p) FROM Patient p")
    Long countTotalPatients();
    
    // Find patients registered today
    @Query("SELECT p FROM Patient p WHERE DATE(p.createdAt) = CURRENT_DATE")
    List<Patient> findPatientsRegisteredToday();
    
    // Find patients registered this month
    @Query("SELECT p FROM Patient p WHERE MONTH(p.createdAt) = MONTH(CURRENT_DATE) AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    List<Patient> findPatientsRegisteredThisMonth();
    
    // Find recent patients (last 10)
    @Query("SELECT p FROM Patient p ORDER BY p.createdAt DESC LIMIT 10")
    List<Patient> findRecentPatients();
}