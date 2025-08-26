package com.nepbay.dentalclinicapi.repository;

import com.nepbay.dentalclinicapi.model.Appointment;
import com.nepbay.dentalclinicapi.model.Appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Appointment Repository
 * Handles database operations for Appointment entity
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Find appointments by patient name
    List<Appointment> findByPatientNameContainingIgnoreCase(String patientName);
    
    // Find appointments by date
    List<Appointment> findByAppointmentDate(LocalDate date);
    
    // Find appointments by status
    List<Appointment> findByStatus(AppointmentStatus status);
    
    // Count appointments by status
    Long countByStatus(AppointmentStatus status);
    
    // Find today's appointments
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = CURRENT_DATE ORDER BY a.appointmentTime ASC")
    List<Appointment> findTodayAppointments();
    
    // Find upcoming appointments
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= CURRENT_DATE ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findUpcomingAppointments();
    
    // Find appointments this week
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startDate AND a.appointmentDate <= :endDate ORDER BY a.appointmentDate ASC")
    List<Appointment> findAppointmentsThisWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Find appointments this month
    @Query("SELECT a FROM Appointment a WHERE MONTH(a.appointmentDate) = MONTH(CURRENT_DATE) AND YEAR(a.appointmentDate) = YEAR(CURRENT_DATE)")
    List<Appointment> findAppointmentsThisMonth();
    
    // Count total appointments
    @Query("SELECT COUNT(a) FROM Appointment a")
    Long countTotalAppointments();
    
    // Find recent appointments (last 10)
    @Query("SELECT a FROM Appointment a ORDER BY a.createdAt DESC LIMIT 10")
    List<Appointment> findRecentAppointments();
    
    // Find appointments by date range
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findAppointmentsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}