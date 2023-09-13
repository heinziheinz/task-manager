package service;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class User {
    private final String name;
    private final String role;
    private final ArrayList<Appointment> appointments;
    private final ArrayList<Task> tasks;
    private final CurrentTime currentTime;

    public User(String name, String role, CurrentTime currentTime) {
        this.name = name;
        this.role = role;
        this.appointments = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.currentTime = currentTime;
    }

    public String name() {
        return name;
    }

    public String role() {
        return role;
    }

    public boolean setAppointments(String title, String description, LocalDateTime startDate, LocalDateTime endDate){
        // only if date is not in the past and not at the same time as another Appointment
        LocalDateTime currentDateTime  = currentTime.getCurrentTime();
        boolean notAtSameTime = checkIfDateIsAtTheSameTime(startDate, endDate);
        System.out.println("notAtSameTime = " + notAtSameTime);
        System.out.println("startDate.isBefore(currentDateTime) = " + startDate.isBefore(currentDateTime));

        if (startDate.isBefore(currentDateTime)  && !notAtSameTime) {
            System.out.println("startDate is in the past.");
            return false;
        } else {
            appointments.add(new Appointment(title, description, startDate, endDate));
            return true;
        }

    }

    public boolean checkIfDateIsAtTheSameTime(LocalDateTime startDate, LocalDateTime endDate){
        //TODO: here
        boolean isColliding = appointments.stream()
                .anyMatch(appointment -> {
                    return (appointment.startDate().isBefore(startDate) && appointment.endDate().isAfter(startDate)) ||
                            (appointment.startDate().isBefore(endDate)) && appointment.endDate().isAfter(endDate) ||
                            (appointment.startDate().equals(startDate) && appointment.endDate().equals(endDate))||
                            (appointment.startDate().isAfter(startDate) && appointment.endDate().isBefore(endDate));
                });

        System.out.println("isColliding = " + isColliding);

        return !isColliding;
    }

    public boolean setTask(String title, String description, LocalDateTime date, Status status ){
        tasks.add(new Task(title,description,date,status));
        return true;
    }

    public List<Object> getAllTasksAndAppointmentsOfMonth(){

        LocalDateTime currentDateTime = currentTime.getCurrentTime();
        int currentMonth = currentDateTime.getMonthValue();
        int currentYear = currentDateTime.getYear();

        List<Appointment> apointemntList = appointments.stream()
                .filter(appointment -> appointment.startDate().getMonthValue() == currentMonth && appointment.startDate().getYear() == currentYear).toList();

        List<Task> taskList = tasks.stream()
                .filter(task -> task.date().getMonthValue() == currentMonth && task.date().getYear() == currentYear).toList();

        // Combine the two lists into a single List<Object>
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(apointemntList);
        combinedList.addAll(taskList);

        return combinedList;
    }

    public List<Task> getAllNotCompletedTask(){
        //Tasks which are in the past and are not completed
        return tasks.stream().filter(task -> task.date().isBefore(currentTime.getCurrentTime()) && task.status().equals(Status.PENDING)).toList();
    }

    public void setTasks(String title, String description, LocalDateTime dueDate, Status status){
        tasks.add(new Task( title,  description,  dueDate,status));
    }





    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    @Override
    public String toString() {
        return "User[" +
                "name=" + name + ", " +
                "role=" + role + ']';
    }

}
