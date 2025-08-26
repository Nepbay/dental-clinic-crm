package com.nepbay.dentalclinicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Dental Clinic API - Main Application
 * Created by: Nepbay
 * Date: 2025-08-26 12:12:48 UTC
 * 
 * This is a REST API for managing dental clinic operations
 * including patients, appointments, and dashboard analytics.
 */
@SpringBootApplication
public class DentalClinicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApiApplication.class, args);
		
		System.out.println("\n🦷 =====================================");
		System.out.println("🚀 DENTAL CLINIC API STARTED!");
		System.out.println("👨‍💻 Created by: Nepbay");
		System.out.println("📅 Date: 2025-08-26 12:12:48 UTC");
		System.out.println("🌐 API Base: http://localhost:8080/api");
		System.out.println("🗄️ Database: http://localhost:8080/h2-console");
		System.out.println("❤️  Health: http://localhost:8080/actuator/health");
		System.out.println("📊 Status: READY FOR CONNECTIONS!");
		System.out.println("🦷 =====================================\n");
	}
}