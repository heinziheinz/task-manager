package service;

import model.Appointment;
import model.CurrentTimeMockImpl;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;

class CreateInputsTest {


    @org.junit.jupiter.api.Test
    void setAppointments() {


        TaskManager createInputs = new TaskManager(new CurrentTimeMockImpl(2023, 9, 13, 14, 30, 0));
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 13, 10, 49, 45);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 13, 16, 30, 45);
        Appointment expected = new Appointment("Dentist", "It`s due man", startDate, endDate);
        Set<Appointment> appointmentList = new HashSet<>();

        boolean actual = createInputs.setAppointments("Dentist", "It`s due man", startDate, endDate, appointmentList);


        Assertions.assertEquals(true, actual);
    }



    //    @ParameterizedTest
//    @MethodSource("parameters")
    @org.junit.jupiter.api.Test
    void getAppointmentsAndTasksOfThisMonth() {
        // Create tasks
        LocalDateTime cleaningTime = LocalDateTime.of(2023, 9, 24, 15, 30);
        Task cleaningHouse = new Task("Putzen", "anstrenden", cleaningTime, Status.COMPLETED);
        List<Task> taskList = List.of(cleaningHouse);

        // Create Appointments
        LocalDateTime newStartDate = LocalDateTime.of(2023, 9, 24, 15, 30);
        LocalDateTime newEndDate = LocalDateTime.of(2023, 9, 24, 15, 30);
        Appointment appointment = new Appointment("Vistit", "nice", newStartDate, newEndDate);
        List<Appointment> newAppointmentList = List.of(appointment);

        TaskManager taskManager = new TaskManager(new CurrentTimeMockImpl(2023, 9, 13, 14, 30, 0));

        List<Object> actual = taskManager.getAllTasksAndAppointmentsOfMonth(taskList, newAppointmentList);
//        System.out.println("tasksAndAppointments = " + tasksAndAppointments);
        List<Object> expected = List.of(appointment, cleaningHouse);

        assertEquals(expected , actual);

    }
    @org.junit.jupiter.api.Test
    public void getAllNotCompletedTask() {


        LocalDateTime cleaningTime = LocalDateTime.of(2022, 9, 23, 15, 30);
        Task cleaningHouse = new Task("Putzen", "anstrenden", cleaningTime, Status.PENDING);
        List<Task> expectedTaskList = List.of(cleaningHouse);

        TaskManager taskManager = new TaskManager(new CurrentTimeMockImpl(2023, 9, 24, 14, 30, 0));

//   - Create a function which retrieves all tasks which are in the past and not completed
        List<Task> actual = taskManager.getAllNotCompletedTask(expectedTaskList );

        assertEquals(expectedTaskList, actual);

    }


}