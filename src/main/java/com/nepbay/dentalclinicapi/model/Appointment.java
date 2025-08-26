package com.nepbay.dentalclinicapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Appointment Entity
 * Represents an appointment in the dental clinic system
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Patient name is required")
    @Size(max = 100, message = "Patient name cannot be longer than 100 characters")
    @Column(name = "patient_name", nullable = false, length = 100)
    private String patientName;
    
    @NotNull(message = "Appointment date is required")
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;
    
    @Column(name = "appointment_time")
    private LocalTime appointmentTime;
    
    @Size(max = 200, message = "Treatment description cannot be longer than 200 characters")
    @Column(length = 200)
    private String treatment;
    
    @Size(max = 1000, message = "Notes cannot be longer than 1000 characters")
    @Column(length = 1000)
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enum for appointment status
    public enum AppointmentStatus {
        SCHEDULED,
        CONFIRMED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED,
        NO_SHOW,
        RESCHEDULED
    }
    
    // Constructors
    public Appointment() {}
    
    public Appointment(String patientName, LocalDate appointmentDate) {
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.status = AppointmentStatus.SCHEDULED;
    }
    
    public Appointment(String patientName, LocalDate appointmentDate, LocalTime appointmentTime, String treatment) {
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.treatment = treatment;
        this.status = AppointmentStatus.SCHEDULED;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = AppointmentStatus.SCHEDULED;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", treatment='" + treatment + '\'' +
                ", status=" + status +
                '}';
    }
}