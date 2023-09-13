package model;

import java.time.LocalDateTime;
import java.util.Date;

public record Task(String title, String description, LocalDateTime date, Status status) {
}
