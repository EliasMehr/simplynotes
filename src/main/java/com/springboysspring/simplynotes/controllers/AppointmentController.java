package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.Appointment;
import com.springboysspring.simplynotes.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/appointments/user")
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET ALL APPOINTMENTS FOR USER
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable UUID id) {
        try {
            List<Appointment> allAppointments = appointmentService.getAllAppointmentsByUser(id);
            return ResponseEntity.ok(allAppointments);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // CREATE APPOINTMENTS FOR USER

    @PreAuthorize("'USER'")
    @Transactional
    @PostMapping("/{id}")
    public ResponseEntity<String> create(@PathVariable UUID id, @RequestBody Appointment appointment) {
        try {
            appointmentService.add(id, appointment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Appointment has been successfully added");
    }

    // DELETE APPOINTMENT FOR USER
    @Transactional
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        try {
            appointmentService.delete(id);
            return ResponseEntity.ok("This appointment has been deleted successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    // UPDATE APPOINTMENT FOR USER
    @Transactional
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Appointment appointment) {
        try {
            appointmentService.update(id, appointment);
            return ResponseEntity.ok("This appointment has been successfully updated");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
