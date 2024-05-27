package com.example.rest.dto;

import com.example.rest.domain.Priority;
import com.example.rest.domain.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoDTO {
     String id;
     String title;
     String description;
     String createdBy;
     String assignedTo;
     Status status;
     Priority priority;
     LocalDate created;
}
