package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.input.CreateInputs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;

class CreateInputsTest {

    @Test
    void setAppointments() {
        CreateInputs createInputs = new CreateInputs();
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 13, 10, 49, 45);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 13, 16, 30, 45);
        
        boolean expected = createInputs.setAppointments("Dentist", "It`s due man",startDate, endDate  );
        
        assertTrue(expected);
    }

    @Test
    void checkIfDateIsAtTheSameTime() {
        CreateInputs createInputs = new CreateInputs();
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 13, 10, 49, 45);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 13, 16, 30, 45);
        boolean expected = createInputs.setAppointments("Dentist", "It`s due man",startDate, endDate  );
        System.out.println(expected);
// 10 - 16


        //1.  9- 15  before
        LocalDateTime startDateTwo = LocalDateTime.of(2024, 9, 13, 9, 49, 45);
        LocalDateTime endDateTwo = LocalDateTime.of(2024, 9, 13, 15, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTime = createInputs.checkIfDateIsAtTheSameTime(startDateTwo, endDateTwo);


        assertFalse(checkIfAppointmentIsAreAtTheSameTime);

        // 11 -17 after

        LocalDateTime startDateThree = LocalDateTime.of(2024, 9, 13, 11, 49, 45);
        LocalDateTime endDateThree = LocalDateTime.of(2024, 9, 13, 17, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTimeTwo = createInputs.checkIfDateIsAtTheSameTime(startDateThree, endDateThree);
        System.out.println("checkIfAppointmentIsAreAtTheSameTimeTwo = " + checkIfAppointmentIsAreAtTheSameTimeTwo);

        assertFalse(checkIfAppointmentIsAreAtTheSameTimeTwo);

        //10 - 16 exact
        LocalDateTime startDateFour = LocalDateTime.of(2024, 9, 13, 10, 49, 45);
        LocalDateTime endDateFour = LocalDateTime.of(2024, 9, 13, 16, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTimeThree = createInputs.checkIfDateIsAtTheSameTime(startDateFour,endDateFour);
        System.out.println("checkIfAppointmentIsAreAtTheSameTimeTwo = " + checkIfAppointmentIsAreAtTheSameTimeThree);

        assertFalse(checkIfAppointmentIsAreAtTheSameTimeThree);


        //9 - 17 encompassing
        LocalDateTime startDateFive = LocalDateTime.of(2024, 9, 13, 9, 49, 45);
        LocalDateTime endDateFive = LocalDateTime.of(2024, 9, 13, 17, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTimeFive = createInputs.checkIfDateIsAtTheSameTime(startDateFive , endDateFive);

        assertFalse(checkIfAppointmentIsAreAtTheSameTimeFive);

        //8 -9 before
        LocalDateTime startDateSix = LocalDateTime.of(2024, 9, 13, 8, 49, 45);
        LocalDateTime endDateSix = LocalDateTime.of(2024, 9, 13, 9, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTimeSix = createInputs.checkIfDateIsAtTheSameTime(startDateSix , endDateSix);

        assertTrue(checkIfAppointmentIsAreAtTheSameTimeSix );

        //17 -18 after
        LocalDateTime startDateSeven = LocalDateTime.of(2024, 9, 13, 17, 49, 45);
        LocalDateTime endDateSeven = LocalDateTime.of(2024, 9, 13, 18, 30, 45);

        boolean checkIfAppointmentIsAreAtTheSameTimeSeven = createInputs.checkIfDateIsAtTheSameTime(startDateSeven, endDateSeven);

        assertTrue(checkIfAppointmentIsAreAtTheSameTimeSeven );

    }


    public static Stream<Arguments> parameters() {
        return Stream.of(
                // Sprint #1
                of(new CreateInputs(),  LocalDateTime.of(2023, 9, 13, 10, 49, 45),LocalDateTime.of(2023, 9, 13, 16, 30, 45), true),
                of(new CreateInputs(),  LocalDateTime.of(2023, 8, 31, 10, 49, 45),LocalDateTime.of(2023, 8, 31, 16, 30, 45), false),
                of(new CreateInputs(),  LocalDateTime.of(2023, 9, 1, 0, 0, 45),LocalDateTime.of(2023, 9, 1, 16, 30, 45), true)

        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    void getAppointmentsAndTasks(CreateInputs createInputs, LocalDateTime startDate,  LocalDateTime endDate, boolean expectZeroOrGreater ) {

        boolean expected = createInputs.setAppointments("Dentist", "It`s due man",startDate, endDate  );

        boolean expected2 = createInputs.setTask("Putzen","Putzen", startDate, Status.COMPLETED);

        List<Object> tasksAndAppointments =  createInputs.getAllTasksAndAppointmentsOfMonth();
        System.out.println("tasksAndAppointments = " + tasksAndAppointments);

        assertEquals(expectZeroOrGreater, expected);
        assertEquals(expectZeroOrGreater, expected2);
        if (expectZeroOrGreater) {
            assertTrue(!tasksAndAppointments.isEmpty());
        } else {
            assertEquals(0, tasksAndAppointments.size());
        }

    }

    public static Stream<Arguments> parametersTwo() {
        return Stream.of(
                // Sprint #1
                of(new AppointmentService("Frank", "Dentist", new TimeServiceMockImpl(2023, 9, 13, 14, 30, 0)),  "putzen", "genau",LocalDateTime.of(2023, 9, 13, 16, 30, 45), Status.COMPLETED, true),
                of(new AppointmentService("Jack", "Oncologist",new TimeServiceMockImpl(2023, 9, 13, 14, 30, 0)),  "putzen", "genau",LocalDateTime.of(2023, 8, 13, 16, 30, 45), Status.PENDING, false)

        );
    }

    @ParameterizedTest
    @MethodSource("parametersTwo")
    public void getAllNotCompletedTask(AppointmentService createInputs, String title, String description, LocalDateTime date, Status taskCompleted, boolean isNotEmpty ){

        boolean expected2 = createInputs.setTask(title,description, date, taskCompleted);

        List<Task> allNotCompletedTasks = createInputs.getAllNotCompletedTask();

        System.out.println("expected2 = " + allNotCompletedTasks.size());

        if(isNotEmpty){
            assertEquals(0, allNotCompletedTasks.size());
        }else {
            assertTrue(!allNotCompletedTasks.isEmpty());
        }
    }




}