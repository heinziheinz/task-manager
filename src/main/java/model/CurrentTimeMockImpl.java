package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CurrentTimeMockImpl implements CurrentTime{
    private final LocalDateTime currentDate;

    public CurrentTimeMockImpl(int year, int month, int day, int hour, int minute, int second) {
        this.currentDate = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    @Override
    public LocalDateTime getCurrentTime() {
//        return LocalDateTime.of(year, month, day, hour, minute, second);
        return currentDate;
    }
}
