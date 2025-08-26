package com.nepbay.dentalclinicapi.service;

import com.nepbay.dentalclinicapi.model.Appointment;
import com.nepbay.dentalclinicapi.model.Appointment.AppointmentStatus;
import com.nepbay.dentalclinicapi.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

/**
 * Appointment Service
 * Business logic for Appointment operations
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    /**
     * Get all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    /**
     * Get appointment by ID
     */
    public Appointment getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.orElse(null);
    }
    
    /**
     * Create new appointment
     */
    public Appointment createAppointment(Appointment appointment) {
        // Validate appointment date
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot schedule appointment in the past");
        }
        
        // Set default treatment if not provided
        if (appointment.getTreatment() == null || appointment.getTreatment().isEmpty()) {
            appointment.setTreatment("General Consultation");
        }
        
        return appointmentRepository.save(appointment);
    }
    
    /**
     * Update existing appointment
     */
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(id);
        if (existingAppointment == null) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        
        // Validate new appointment date
        if (updatedAppointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot schedule appointment in the past");
        }
        
        // Update fields
        existingAppointment.setPatientName(updatedAppointment.getPatientName());
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        existingAppointment.setTreatment(updatedAppointment.getTreatment());
        existingAppointment.setNotes(updatedAppointment.getNotes());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        
        return appointmentRepository.save(existingAppointment);
    }
    
    /**
     * Delete appointment
     */
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
    
    /**
     * Update appointment status
     */
    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status) {
        Appointment appointment = getAppointmentById(id);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    
    /**
     * Search appointments by patient name
     */
    public List<Appointment> searchAppointmentsByPatient(String patientName) {
        return appointmentRepository.findByPatientNameContainingIgnoreCase(patientName);
    }
    
    /**
     * Get appointment statistics and filters
     */
    public List<Appointment> getTodayAppointments() {
        return appointmentRepository.findTodayAppointments();
    }
    
    public List<Appointment> getUpcomingAppointments() {
        return appointmentRepository.findUpcomingAppointments();
    }
    
    public List<Appointment> getAppointmentsThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        return appointmentRepository.findAppointmentsThisWeek(startOfWeek, endOfWeek);
    }
    
    public List<Appointment> getAppointmentsThisMonth() {
        return appointmentRepository.findAppointmentsThisMonth();
    }
    
    public Long getTotalAppointmentCount() {
        return appointmentRepository.countTotalAppointments();
    }
    
    public Long getAppointmentCountByStatus(AppointmentStatus status) {
        return appointmentRepository.countByStatus(status);
    }
    
    public List<Appointment> getRecentAppointments() {
        return appointmentRepository.findRecentAppointments();
    }
    
    public List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findAppointmentsByDateRange(startDate, endDate);
    }
}