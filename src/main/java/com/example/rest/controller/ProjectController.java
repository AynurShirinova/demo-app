package com.example.rest.controller;

import com.example.rest.domain.Project;
import com.example.rest.dto.EntityByIdDTO;
import com.example.rest.dto.ProjectDTO;
import com.example.rest.exceptions.CustomException;
import com.example.rest.service.ProjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Validated
@RequestMapping("/projects")
@SuppressWarnings("ALL")
@Builder
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    ProjectService projectService;

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

   @GetMapping("/projectsId")
    public ResponseEntity<?> getProjectById(@RequestBody EntityByIdDTO dto) {
        try {
            return projectService.getProjectById(dto.getId());

        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<String> createNewProject(@RequestBody ProjectDTO dto) {
        return projectService.insertNewProject(dto);

    }
    @GetMapping("/private")
    public String helloWorldPrivate() {
        return "Hello World! from private endpoint";
    }
}
