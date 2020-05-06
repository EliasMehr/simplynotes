package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
