package service;

import model.Activities;
import model.Appointment;
import model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class ActivityService {
    public Activities findActivitiesWithinMonth(Set<Task> tasks, Set<Appointment> appointments, LocalDateTime date) {
        Set<Task> thisMonthTasks = tasks.stream()
                .filter(task -> isWithinSameMonthOfSameYear(task.date(), date))
                .collect(Collectors.toSet());
        Set<Appointment> thisMonthAppointments = appointments.stream()
                .filter(appointment -> isWithinSameMonthOfSameYear(appointment.startDate(), date))
                .collect(Collectors.toSet());
        return new Activities(thisMonthTasks, thisMonthAppointments);
    }

    private boolean isWithinSameMonthOfSameYear(LocalDateTime date, LocalDateTime target) {
        return date.getMonthValue() == target.getMonthValue() &&
                date.getYear() == target.getYear();
    }
}
