package service;

import model.Appointment;

import java.time.LocalDateTime;

public class TimeSlotService {
    public boolean overlaps(Appointment appointment, Appointment target) {
        LocalDateTime appointmentStartDate = appointment.startDate();
        LocalDateTime appointmentEndDate = appointment.endDate();
        LocalDateTime targetStartDate = target.startDate();
        LocalDateTime targetEndDate = target.endDate();
        return appointmentStartDate.isBefore(targetEndDate) &&
                targetStartDate.isBefore(appointmentEndDate);
    }
}
