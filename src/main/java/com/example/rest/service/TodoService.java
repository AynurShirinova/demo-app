package com.example.rest.service;

import com.example.rest.domain.Todo;
import com.example.rest.dto.BetweenDateDTO;
import com.example.rest.dto.TodoDTO;
import com.example.rest.exceptions.CustomException;
import com.example.rest.repository.TodosRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TodoService {
    private final TodosRepository todosRepository;
public void addTask(TodoDTO dto) {
    Todo todo = new Todo();
    todo.setId(UUID.randomUUID().toString());
    todo.setAssignedTo(dto.getAssignedTo());
    todo.setCreated(dto.getCreated());
    todo.setCreatedBy(dto.getCreatedBy());
    todo.setDescription(dto.getDescription());
    todo.setPriority(dto.getPriority());
    todo.setId(dto.getId());
    todo.setStatus(dto.getStatus());
    todo.setTitle(dto.getTitle());
    try {
        todosRepository.save(todo);
    } catch (DataIntegrityViolationException e) {
        throw new CustomException("Bu görev zaten eklenmiş.");
    } catch (DataAccessException e) {
        throw new CustomException("Veritabanı işleminde bir hata oluştu.");
    }
}

public List<Todo> readTasks() {
    try {
        return todosRepository.findAll();
    } catch (DataAccessException e) {
        throw new CustomException("Veritabanı işleminde bir hata oluştu.");
    }
}
    public void deleteTask(String id) {
        try {
            todosRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException("Silinmek istenen görev bulunamadı.");
        } catch (DataAccessException e) {
            throw new CustomException("Veritabanı işleminde bir hata oluştu.");
        }
    }

    public void updateTask(TodoDTO dto) {
        try {
            Optional<Todo> optionalTodo = todosRepository.findById(dto.getId());
            if (optionalTodo.isPresent()) {
                Todo todo = optionalTodo.get();
                todo.setTitle(dto.getTitle());
                todo.setDescription(dto.getDescription());
                todo.setPriority(dto.getPriority());
                todo.setStatus(dto.getStatus());
                todo.setAssignedTo(dto.getAssignedTo());
                todo.setCreatedBy(dto.getCreatedBy());
                todosRepository.save(todo);
            } else {
                throw new CustomException("Güncellenmek istenen görev bulunamadı.");
            }
        } catch (DataAccessException e) {
            throw new CustomException("Veritabanı işleminde bir hata oluştu.");
        }
    }
   public List<Todo> getTodosBetweenDates(BetweenDateDTO dto) {
       try {
        return todosRepository.getTodosBetweenDates(dto.getStartDate(), dto.getEndDate());
    } catch (DataAccessException e) {
        throw new CustomException("Veritabanı işleminde bir hata oluştu.");
    }
}
    public ResponseEntity<?> listTasksByAssignedTo(String assignedTo) {
        try {
            Optional<Todo> optionalTodo = todosRepository.findByAssignedTo(assignedTo);
            if (optionalTodo.isPresent()) {
                Todo todo = optionalTodo.get();
                return ResponseEntity.ok(todo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Task not found with id: " + assignedTo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the project");
        }
    }
}



