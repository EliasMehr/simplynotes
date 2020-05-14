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
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET ALL APPOINTMENTS FOR USER
    @PreAuthorize("hasRole('USER')")
    @GetMapping("manage-appointment/user/{id}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable UUID id) {
        try {
            List<Appointment> allAppointments = appointmentService.getAllAppointmentsByUser(id);
            return ResponseEntity.ok(allAppointments);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // CREATE APPOINTMENTS FOR USER
    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping("manage-appointment/{id}")
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
    @DeleteMapping("manage-appointment/{id}")
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
    @PutMapping("manage-appointment/{id}")
    public ResponseEntity<String> update(@PathVariable  UUID id, @RequestBody Appointment appointment) {
        try {
            appointmentService.update(id, appointment);
            return ResponseEntity.ok("This appointment has been successfully updated");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // ADD AN ATTENDEE TO A EXISTING APPOINTMENT
    @Transactional
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/manage-appointment/{appointmentId}")
    public ResponseEntity<String> addAttendee(@PathVariable UUID appointmentId, @RequestParam(name = "add") UUID attendeeId) {
        try {
            appointmentService.addAttendee(appointmentId, attendeeId);
            return ResponseEntity.ok("Added Attendee with id: " + attendeeId + " successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    // REMOVE AN ATTENDEE FROM AN EXISTING APPOINTMENT
    @Transactional
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/manage-appointment/{appointmentId}/manage-attendee")
    public ResponseEntity<String> removeAttendee(@PathVariable UUID appointmentId, @RequestParam(name = "remove") UUID attendeeId) {
        try {
            appointmentService.remove(appointmentId, attendeeId);
            return ResponseEntity.ok("Successfully removed: " + attendeeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't remove attendee");
        }
    }
}
