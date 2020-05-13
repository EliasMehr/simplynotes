package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.Appointment;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.AppointmentRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AppointmentService {

    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // GET APPOINTMENT FOR USER
    public List<Appointment> getAllAppointmentsByUser(UUID id) throws Exception {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            return appointmentRepository.findAllByAttendees(userById.get());
        }
        throw new Exception("Could not find any appointments");
    }

    // CREATE APPOINTMENTS FOR USER
    public void add(UUID id, Appointment appointment) throws Exception {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            userById.get().addAppointment(appointment);
        }
        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new Exception("Could not add appointment");
        }
    }

    // DELETE APPOINTMENT FOR USER
    public void delete(UUID id) throws Exception {
        try {
            appointmentRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("No appointment exists with id: " + id);
        }
    }

    // UPDATE APPOINTMENT FOR USER
    public void update(UUID id, Appointment appointment) throws Exception {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);

        if (existingAppointment.isPresent()) {
            existingAppointment.get().setTitle(appointment.getTitle());
            existingAppointment.get().setDescription(appointment.getDescription());
//            existingAppointment.get().setAppointmentTime(appointment.getAppointmentTime());
            existingAppointment.get().setAttendees(appointment.getAttendees());
            existingAppointment.get().setEstimatedTime(appointment.getEstimatedTime());
            try {
                appointmentRepository.save(existingAppointment.get());
            } catch (Exception e) {
                throw new Exception("Could not update this appointment");
            }
        } else {
            throw new Exception("No Appointment with following id; " + id + " exists");
        }
    }
}
