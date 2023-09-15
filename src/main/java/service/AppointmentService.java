package service;

import model.Appointment;

import java.util.Set;

public class AppointmentService {
    private final TimeSlotService timeSlotService;

    public AppointmentService(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    public void add(Set<Appointment> appointments, Appointment appointment) {
        boolean doesNotOverlap = appointments.stream()
                .noneMatch(a -> timeSlotService.overlaps(a, appointment));
        if (doesNotOverlap) {
            appointments.add(appointment);
        }
    }
}
