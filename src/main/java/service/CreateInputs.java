package service;

import model.Appointment;
import model.Status;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class CreateInputs {
    private final ArrayList<Appointment> appointments;
    private final ArrayList<Task> tasks;

    public CreateInputs() {
        this.appointments = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public boolean setAppointments( String title, String description, LocalDateTime startDate, LocalDateTime endDate){
        // only if date is not in the past and not at the same time as another Appointment
        LocalDateTime currentDateTime  = getCurrentTime();
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

    public boolean setTask(String title, String description, LocalDateTime date, Status status ){
        tasks.add(new Task(title,description,date,status));
        return true;
    }

    public  List<Object> getAllTasksAndAppointmentsOfMonth(){

        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentMonth = currentDateTime.getMonthValue();
        int currentYear = currentDateTime.getYear();

        List<Appointment> apointemntList = appointments.stream()
                .filter(appointment -> appointment.startDate().getMonthValue() == currentMonth && appointment.startDate().getYear() == currentYear).toList();

         List<Task> taskList = tasks.stream()
                 .filter(task -> task.date().getMonthValue() == currentMonth && task.date().getYear() == currentYear).toList();

        // Combine the two lists into a single List<Object>
        //interface eingentlich
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(apointemntList);
        combinedList.addAll(taskList);

        return combinedList;
    }



    private  LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }
   public boolean checkIfDateIsAtTheSameTime(LocalDateTime startDate, LocalDateTime endDate){
        //TODO: here
       System.out.println("appointments" + appointments.size());
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

    public List<Task> getAllNotCompletedTask(){
        //Tasks which are in the past and are not completed
        return tasks.stream().filter(task -> task.date().isBefore(getCurrentTime()) && task.status().equals(Status.PENDING)).toList();
    }




    public void setTasks(String title, String description, LocalDateTime dueDate, Status status){
        tasks.add(new Task( title,  description,  dueDate,status));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CreateInputs[]";
    }


}
