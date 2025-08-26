package com.nepbay.dentalclinicapi.config;

import com.nepbay.dentalclinicapi.model.Patient;
import com.nepbay.dentalclinicapi.model.Appointment;
import com.nepbay.dentalclinicapi.repository.PatientRepository;
import com.nepbay.dentalclinicapi.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Initializer
 * Adds sample data when application starts
 * Created by: Nepbay on 2025-08-26 12:12:48 UTC
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (patientRepository.count() == 0) {
            System.out.println("🔄 Adding sample data to dental clinic database...");
            
            // Create sample patients
            Patient[] patients = {
                new Patient("John Doe", "0555-123-4567", "john.doe@email.com", "123 Main St, Istanbul"),
                new Patient("Jane Smith", "0555-987-6543", "jane.smith@email.com", "456 Oak Ave, Ankara"),
                new Patient("Ahmet Yılmaz", "0555-456-7890", "ahmet.yilmaz@email.com", "789 Pine Rd, Izmir"),
                new Patient("Fatma Kaya", "0555-321-0987", "fatma.kaya@email.com", "321 Elm St, Bursa"),
                new Patient("Mike Johnson", "0555-654-3210", "mike.johnson@email.com", "654 Maple Dr, Antalya"),
                new Patient("Ayşe Demir", "0555-789-0123", "ayse.demir@email.com", "987 Cedar Ln, Adana"),
                new Patient("Sarah Wilson", "0555-234-5678", "sarah.wilson@email.com", "234 Birch St, Gaziantep"),
                new Patient("Mehmet Özkan", "0555-876-5432", "mehmet.ozkan@email.com", "876 Spruce Ave, Konya")
            };
            
            // Save patients
            for (Patient patient : patients) {
                patientRepository.save(patient);
            }
            
            // Create sample appointments
            Appointment[] appointments = {
                new Appointment("John Doe", LocalDate.now().plusDays(1), LocalTime.of(9, 0), "Regular Checkup"),
                new Appointment("Jane Smith", LocalDate.now().plusDays(1), LocalTime.of(10, 30), "Teeth Cleaning"),
                new Appointment("Ahmet Yılmaz", LocalDate.now().plusDays(2), LocalTime.of(14, 0), "Tooth Filling"),
                new Appointment("Fatma Kaya", LocalDate.now().plusDays(2), LocalTime.of(15, 30), "Root Canal"),
                new Appointment("Mike Johnson", LocalDate.now().plusDays(3), LocalTime.of(9, 30), "Orthodontic Consultation"),
                new Appointment("Ayşe Demir", LocalDate.now().plusDays(3), LocalTime.of(11, 0), "Wisdom Tooth Extraction"),
                new Appointment("Sarah Wilson", LocalDate.now().plusDays(4), LocalTime.of(13, 30), "Dental Implant Consultation"),
                new Appointment("Mehmet Özkan", LocalDate.now().plusDays(5), LocalTime.of(10, 0), "Periodontal Treatment"),
                new Appointment("John Doe", LocalDate.now().plusDays(7), LocalTime.of(14, 30), "Follow-up Checkup"),
                new Appointment("Jane Smith", LocalDate.now().plusDays(8), LocalTime.of(16, 0), "Teeth Whitening")
            };
            
            // Save appointments
            for (Appointment appointment : appointments) {
                appointmentRepository.save(appointment);
            }
            
            System.out.println("✅ Sample data added successfully!");
            System.out.println("👥 Patients: " + patientRepository.count());
            System.out.println("📅 Appointments: " + appointmentRepository.count());
            System.out.println("🚀 Dental Clinic API is ready with sample data!");
        } else {
            System.out.println("📊 Database already contains data, skipping initialization.");
        }
    }
}