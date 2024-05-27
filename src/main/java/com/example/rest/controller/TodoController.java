package com.example.rest.controller;

import com.example.rest.domain.Todo;
import com.example.rest.dto.BetweenDateDTO;
import com.example.rest.dto.EntityByIdDTO;
import com.example.rest.dto.TodoDTO;
import com.example.rest.exceptions.CustomException;
import com.example.rest.service.TodoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("ALL")
@Builder
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TodoController {
    TodoService todoService;
    Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "Admin erişim noktası";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "User erişim noktası";
    }
    @GetMapping("/read")
    public ResponseEntity<?> readTasks() {
        try {
            List<Todo> tasks = todoService.readTasks();
            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found.");
            } else {
                return ResponseEntity.ok(tasks);
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı işleminde bir hata oluştu.");
        }
    }

@PostMapping ("/add")
public ResponseEntity<?> addTask(@RequestBody TodoDTO dto) {
    try {
        // Oturum açmış kullanıcının kimliğini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // DTO'ya oluşturan kullanıcıyı ekle
        dto.setCreatedBy(currentUserName);

        // Görevi ekle
        todoService.addTask(dto);
        return ResponseEntity.ok("Task added successfully.");
    } catch (CustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

@GetMapping("/betweenDates")
public ResponseEntity<?> getTodosBetweenDates(@RequestBody BetweenDateDTO dto) {
    try {
        List<Todo> filteredTodos = todoService.getTodosBetweenDates(dto);
        if (filteredTodos.isEmpty()) {
            return ResponseEntity.ok("No tasks found.");
        } else {
            return ResponseEntity.ok(filteredTodos);
        }
    } catch (DateTimeParseException e) {
        return ResponseEntity.badRequest().body("Invalid date format. Please enter date in dd/MM/yyyy format.");
    } catch (CustomException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}

   @PutMapping("/update")
   public ResponseEntity<?> updateTask(@RequestBody TodoDTO dto) {
    try {
        todoService.updateTask(dto);
        return ResponseEntity.ok("Task updated successfully.");
    } catch (CustomException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input. Please try again.");
    }
}
    @GetMapping("/assignedTo")
        public ResponseEntity<?> listTasksByAssignedTo(@RequestBody EntityByIdDTO dto) {
            return todoService.listTasksByAssignedTo(dto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        try {
            todoService.deleteTask(id);
            return ResponseEntity.ok("Task deleted successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input. Please try again.");
        }
    }

}
