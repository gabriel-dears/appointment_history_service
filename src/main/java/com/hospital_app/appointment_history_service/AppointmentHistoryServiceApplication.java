package com.hospital_app.appointment_history_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hospital_app"})
public class AppointmentHistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentHistoryServiceApplication.class, args);
	}

}
