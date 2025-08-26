package com.nepbay.dentalclinicapi.controller;

import com.nepbay.dentalclinicapi.model.Appointment;
import com.nepbay.dentalclinicapi.model.Appointment.AppointmentStatus;
import com.nepbay.dentalclinicapi.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Appointment Controller
 * REST API endpoints for Appointment operations
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * GET /api/appointments - Get all appointments
     */
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/{id} - Get appointment by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (appointment != null) {
                return ResponseEntity.ok(appointment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /api/appointments - Create new appointment
     */
    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not create appointment");
        }
    }
    
    /**
     * PUT /api/appointments/{id} - Update appointment
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not update appointment");
        }
    }
    
    /**
     * DELETE /api/appointments/{id} - Delete appointment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok("Appointment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not delete appointment");
        }
    }
    
    /**
     * PATCH /api/appointments/{id}/status - Update appointment status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not update appointment status");
        }
    }
    
    /**
     * GET /api/appointments/search?patient={name} - Search appointments by patient
     */
    @GetMapping("/search")
    public ResponseEntity<List<Appointment>> searchAppointments(@RequestParam String patient) {
        try {
            List<Appointment> appointments = appointmentService.searchAppointmentsByPatient(patient);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/today - Get today's appointments
     */
    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getTodayAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getTodayAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/upcoming - Get upcoming appointments
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<Appointment>> getUpcomingAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getUpcomingAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/week - Get this week's appointments
     */
    @GetMapping("/week")
    public ResponseEntity<List<Appointment>> getThisWeekAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsThisWeek();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/month - Get this month's appointments
     */
    @GetMapping("/month")
    public ResponseEntity<List<Appointment>> getThisMonthAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsThisMonth();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/appointments/date-range - Get appointments by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}