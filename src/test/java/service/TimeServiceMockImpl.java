package service;

import service.time.TimeService;

import java.time.LocalDateTime;

public class TimeServiceMockImpl implements TimeService {
    private final LocalDateTime currentDate;

    public TimeServiceMockImpl(int year, int month, int day, int hour, int minute, int second) {
        this.currentDate = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    @Override
    public LocalDateTime getTime() {
//        return LocalDateTime.of(year, month, day, hour, minute, second);
        return currentDate;
    }
}
