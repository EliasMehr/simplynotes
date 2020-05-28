package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.Appointment;
import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.AppointmentRepository;
import com.springboysspring.simplynotes.repositories.FriendshipRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.handlers.FriendshipHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.springboysspring.simplynotes.models.FriendshipStatus.*;


@Service
public class AppointmentService {

    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;
    private FriendshipHandler friendshipHandler;
    private FriendshipRepository friendshipRepository;

    @Autowired
    public AppointmentService(UserRepository userRepository, AppointmentRepository appointmentRepository, FriendshipHandler friendshipHandler, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.friendshipHandler = friendshipHandler;
        this.friendshipRepository = friendshipRepository;
    }

    // GET APPOINTMENT FOR USER
    public List<Appointment> getAllAppointmentsByUser(UUID userId) throws Exception {
        Optional<User> userById = userRepository.findById(userId);

        if (userById.isPresent()) {
            return appointmentRepository.findAllByAttendees(userById.get());
        }
        throw new Exception("Could not find any appointments");
    }

    // CREATE APPOINTMENTS FOR USER
    public void add(UUID userId, Appointment appointment) throws Exception {
        appointment.setAppointmentTime(appointment.getAppointmentTime().plusHours(2));

        Optional<User> userById = userRepository.findById(userId);

        if (userById.isPresent()) {
            User user = userById.get();
            appointment.addAttendee(user);
        }
        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new Exception("Could not add appointment");
        }
    }

    // DELETE APPOINTMENT FOR USER
    public void delete(UUID appointmentId) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        try {
            appointmentRepository.delete(appointment.get());
        } catch (Exception e) {
            throw new Exception("No appointment exists with id: " + appointmentId);
        }
    }

    // UPDATE APPOINTMENT FOR USER
    public void update(UUID appointmentId, Appointment appointment) throws Exception {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointmentId);

        if (existingAppointment.isPresent()) {
            existingAppointment.get().setTitle(appointment.getTitle());
            existingAppointment.get().setDescription(appointment.getDescription());
            existingAppointment.get().setEstimatedTime(appointment.getEstimatedTime());
            existingAppointment.get().setAppointmentTime(appointment.getAppointmentTime().plusHours(2));
            try {
                Appointment updatedAppointment = existingAppointment.get();
                appointmentRepository.save(updatedAppointment);
            } catch (Exception e) {
                throw new Exception("Could not update this appointment");
            }
        } else {
            throw new Exception("No Appointment with following id: " + appointmentId + " exists");
        }
    }

    // ADD ATTENDEE TO AN APPOINTMENT AFTER CREATED AN APPOINTMENT

    public void addAttendee(UUID appointmentId, UUID currentUser, UUID attendeeId) throws Exception {
        Optional<Appointment> appointmentById = appointmentRepository.findById(appointmentId);
        Optional<User> attendeeById = userRepository.findById(attendeeId);
        Optional<Friendship> byOwnerIdAndFriendId = friendshipRepository.findByOwnerIdAndFriendId(currentUser, attendeeId);

        if (byOwnerIdAndFriendId.isPresent()) {
            boolean friendshipStatus = friendshipHandler.doesFriendshipHaveStatus(byOwnerIdAndFriendId.get(), ACCEPTED);

            if (friendshipStatus) {
                if (appointmentById.isPresent()) {
                    Appointment appointment = appointmentById.get();

                    Optional<User> userOptional = appointment.getAttendees().stream()
                            .filter(attendee -> attendee.getId().equals(attendeeId))
                            .findFirst();

                    if (userOptional.isEmpty()) {
                        appointment.addAttendee(attendeeById.get());
                    } else {
                        throw new Exception("Attendee is already in appointment");
                    }

                    try {
                        appointmentRepository.save(appointment);
                    } catch (Exception e) {
                        throw new Exception("Could not add attendee to appointment");
                    }
                } else {
                    throw new Exception("No Appointment with following id: " + appointmentId + " exists");
                }
            } else {
                throw new Exception("Friend request is still pending");
            }
        } else {
            throw new Exception("You're not friend with selected user");
        }


    }

    // REMOVE AN EXISTING ATTENDEE FROM AN APPOINTMENT
    public void remove(UUID appointmentId, UUID attendeeId) throws Exception {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        Optional<User> attendee = userRepository.findById(attendeeId);
        if (appointment.isPresent()) {
            User user = attendee.get();
            appointment.get().removeAttendee(user);
        }
        try {
            appointmentRepository.save(appointment.get());
        } catch (Exception e) {
            throw new Exception("Could not remove entered user or does not exist");
        }
    }
}
