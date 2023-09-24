package service;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class TaskManager {
    private final CurrentTime currentTime;

    public TaskManager(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }




    public boolean setAppointments(String title, String description, LocalDateTime startDate, LocalDateTime endDate , Set<Appointment>appointmentList){
        //   - Create a function that creates an Appointment only,
        //   if there is not already another appointment at the same time slot and only if the date is not in the past

        LocalDateTime currentDateTime  = currentTime.getCurrentTime();
        boolean notAtSameTime = checkIfDateIsAtTheSameTime(startDate, endDate, appointmentList);
        System.out.println("notAtSameTime = " + notAtSameTime);
        System.out.println("startDate.isBefore(currentDateTime) = " + startDate.isBefore(currentDateTime));

        if (startDate.isBefore(currentDateTime)  && !notAtSameTime) {
            System.out.println("startDate is in the past.");
            return false;
        } else {
            appointmentList.add(new Appointment(title, description, startDate, endDate));
            return true;
        }

    }


    public boolean checkIfDateIsAtTheSameTime(LocalDateTime startDate, LocalDateTime endDate,Set<Appointment>appointmentList){
        //TODO: here
        boolean isColliding = appointmentList.stream()
                .anyMatch(appointment -> {
                    return (appointment.startDate().isBefore(startDate) && appointment.endDate().isAfter(startDate)) ||
                            (appointment.startDate().isBefore(endDate)) && appointment.endDate().isAfter(endDate) ||
                            (appointment.startDate().equals(startDate) && appointment.endDate().equals(endDate))||
                            (appointment.startDate().isAfter(startDate) && appointment.endDate().isBefore(endDate));
                });

        System.out.println("isColliding = " + isColliding);

        return !isColliding;
    }



    public List<Object> getAllTasksAndAppointmentsOfMonth(List<Task> tasksList, List<Appointment> appointmentList){
//        - Create a function which retrieves all tasks and appointments of this month

        LocalDateTime currentDateTime = currentTime.getCurrentTime();
        int currentMonth = currentDateTime.getMonthValue();
        int currentYear = currentDateTime.getYear();

        List<Appointment> apointemntList = appointmentList.stream()
                .filter(appointment -> appointment.startDate().getMonthValue() == currentMonth && appointment.startDate().getYear() == currentYear).toList();

        List<Task> taskList = tasksList.stream()
                .filter(task -> task.date().getMonthValue() == currentMonth && task.date().getYear() == currentYear).toList();

        // Combine the two lists into a single List<Object>
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(apointemntList);
        combinedList.addAll(taskList);

        return combinedList;
    }


    public List<Task> getAllNotCompletedTask(List<Task> taskList){
        //   - Create a function which retrieves all tasks which are in the past and not completed

        return taskList.stream().filter(task -> task.date().isBefore(currentTime.getCurrentTime()) && task.status().equals(Status.PENDING)).toList();
    }


}
