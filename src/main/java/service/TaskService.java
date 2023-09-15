package service;

import model.Status;
import model.Task;
import service.time.TimeService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskService {
    private final TimeService timeService;

    public TaskService(TimeService timeService) {
        this.timeService = timeService;
    }

    public Set<Task> findOldNotCompleted(Set<Task> tasks) {
        LocalDateTime today = timeService.getTime();
        return tasks.stream()
                .filter(task -> task.date().isBefore(today))
                .filter(task -> task.status().equals(Status.PENDING))
                .collect(Collectors.toSet());
    }

}
