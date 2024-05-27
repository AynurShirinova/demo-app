package com.example.rest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "todos")
public class Todo {
    @Id
    String id;
    String title;
    String description;
    String createdBy;
    String assignedTo;
    Status status;
    Priority priority;
    LocalDate created;



}
