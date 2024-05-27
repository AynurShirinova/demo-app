package com.example.rest;

import com.example.rest.domain.Priority;
import com.example.rest.domain.Status;
import com.example.rest.domain.Todo;
import com.example.rest.dto.TodoDTO;
import com.example.rest.exceptions.CustomException;
import com.example.rest.repository.TodosRepository;
import com.example.rest.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestApplicationTests {
	@Mock
	private TodosRepository todosRepository;

	@InjectMocks
	private TodoService todoService;
	@Test
	public void testAddTask() {
		TodoDTO dto = new TodoDTO();
		dto.setTitle("Sample Title");
		dto.setDescription("Sample Description");
		dto.setPriority(Priority.LOW);
		dto.setAssignedTo("efefe");
		dto.setStatus(Status.PROGRESS);
		dto.setCreated(LocalDate.parse("1111-11-11"));
        dto.setCreatedBy("men");
		dto.setId("kjgfjg");

		Todo todo = new Todo();
		todo.setId(UUID.randomUUID().toString());
		todo.setTitle(dto.getTitle());
		todo.setDescription(dto.getDescription());
		todo.setPriority(dto.getPriority());
		todo.setAssignedTo(dto.getAssignedTo());
		todo.setStatus(dto.getStatus());
		todo.setCreated(dto.getCreated());
		todo.setCreatedBy(dto.getCreatedBy());

		when(todosRepository.save(any(Todo.class))).thenReturn(todo);

		assertDoesNotThrow(() -> todoService.addTask(dto));}

		@Test
		public void testAddTask_DataIntegrityViolationException() {
			TodoDTO dto = new TodoDTO();
			dto.setTitle("Sample Title");
			dto.setDescription("Sample Description");
			dto.setPriority(Priority.LOW);
			dto.setAssignedTo("efefe");
			dto.setStatus(Status.PROGRESS);
			dto.setCreated(LocalDate.parse("1111-11-10"));
			dto.setCreatedBy("men");
			dto.setId("kjgfjg");

			when(todosRepository.save(any(Todo.class)))
					.thenThrow(new DataIntegrityViolationException("Bu görev zaten eklenmiş."));

			CustomException exception = assertThrows(CustomException.class, () -> todoService.addTask(dto));
			assertEquals("Bu görev zaten eklenmiş.", exception.getMessage());
		}

		@Test
		public void testAddTask_DataAccessException() {
			TodoDTO dto = new TodoDTO();
			// dto'yu doldur...

			when(todosRepository.save(any(Todo.class))).thenThrow(new DataAccessException("Veritabanı işleminde bir hata oluştu.") {});

			CustomException exception = assertThrows(CustomException.class, () -> todoService.addTask(dto));
			assertEquals("Veritabanı işleminde bir hata oluştu.", exception.getMessage());
		}
	}
