package model;

import java.util.Set;

public record Activities(Set<Task> tasks, Set<Appointment> appointments) {
}
