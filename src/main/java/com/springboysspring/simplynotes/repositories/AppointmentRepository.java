package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Appointment;
import com.springboysspring.simplynotes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByAttendees(User user);
}
