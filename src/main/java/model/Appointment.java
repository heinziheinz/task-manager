package model;

import java.time.LocalDateTime;
import java.util.Date;

public record Appointment(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
}
