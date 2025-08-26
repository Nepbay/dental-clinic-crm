package com.nepbay.dentalclinicapi.controller;

import com.nepbay.dentalclinicapi.model.Appointment.AppointmentStatus;
import com.nepbay.dentalclinicapi.service.PatientService;
import com.nepbay.dentalclinicapi.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard Controller
 * Provides dashboard statistics and analytics
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class DashboardController {
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * GET /api/dashboard/stats - Get comprehensive dashboard statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Patient Statistics
            stats.put("totalPatients", patientService.getTotalPatientCount());
            stats.put("newPatientsToday", patientService.getPatientsRegisteredToday().size());
            stats.put("newPatientsThisMonth", patientService.getPatientsRegisteredThisMonth().size());
            
            // Appointment Statistics
            stats.put("totalAppointments", appointmentService.getTotalAppointmentCount());
            stats.put("todayAppointments", appointmentService.getTodayAppointments().size());
            stats.put("weekAppointments", appointmentService.getAppointmentsThisWeek().size());
            stats.put("monthAppointments", appointmentService.getAppointmentsThisMonth().size());
            stats.put("upcomingAppointments", appointmentService.getUpcomingAppointments().size());
            
            // Appointment Status Counts
            stats.put("scheduledAppointments", appointmentService.getAppointmentCountByStatus(AppointmentStatus.SCHEDULED));
            stats.put("confirmedAppointments", appointmentService.getAppointmentCountByStatus(AppointmentStatus.CONFIRMED));
            stats.put("completedAppointments", appointmentService.getAppointmentCountByStatus(AppointmentStatus.COMPLETED));
            stats.put("cancelledAppointments", appointmentService.getAppointmentCountByStatus(AppointmentStatus.CANCELLED));
            stats.put("noShowAppointments", appointmentService.getAppointmentCountByStatus(AppointmentStatus.NO_SHOW));
            
            // Revenue Estimation (simple calculation)
            Long completedCount = appointmentService.getAppointmentCountByStatus(AppointmentStatus.COMPLETED);
            stats.put("estimatedRevenue", completedCount * 150); // $150 per completed appointment
            
            // Growth Metrics
            stats.put("patientGrowthRate", calculateGrowthRate());
            stats.put("appointmentCompletionRate", calculateCompletionRate());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * GET /api/dashboard/recent-activity - Get recent activity
     */
    @GetMapping("/recent-activity")
    public ResponseEntity<Map<String, Object>> getRecentActivity() {
        try {
            Map<String, Object> activity = new HashMap<>();
            
            activity.put("recentPatients", patientService.getRecentPatients());
            activity.put("recentAppointments", appointmentService.getRecentAppointments());
            activity.put("todayAppointments", appointmentService.getTodayAppointments());
            activity.put("upcomingAppointments", appointmentService.getUpcomingAppointments().stream().limit(5).toList());
            
            return ResponseEntity.ok(activity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * GET /api/dashboard/quick-stats - Get quick statistics for cards
     */
    @GetMapping("/quick-stats")
    public ResponseEntity<Map<String, Object>> getQuickStats() {
        try {
            Map<String, Object> quickStats = new HashMap<>();
            
            quickStats.put("patients", patientService.getTotalPatientCount());
            quickStats.put("todayAppointments", appointmentService.getTodayAppointments().size());
            quickStats.put("weekAppointments", appointmentService.getAppointmentsThisWeek().size());
            quickStats.put("revenue", appointmentService.getAppointmentCountByStatus(AppointmentStatus.COMPLETED) * 150);
            
            return ResponseEntity.ok(quickStats);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    // Helper methods for calculations
    private double calculateGrowthRate() {
        try {
            long thisMonth = patientService.getPatientsRegisteredThisMonth().size();
            // Simple growth rate calculation (this month vs assumed previous month)
            return thisMonth > 0 ? (thisMonth * 100.0 / Math.max(thisMonth, 1)) : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private double calculateCompletionRate() {
        try {
            Long total = appointmentService.getTotalAppointmentCount();
            Long completed = appointmentService.getAppointmentCountByStatus(AppointmentStatus.COMPLETED);
            return total > 0 ? (completed * 100.0 / total) : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}