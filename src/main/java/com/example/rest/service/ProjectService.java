package com.example.rest.service;

import com.example.rest.domain.Project;
import com.example.rest.dto.ProjectDTO;
import com.example.rest.exceptions.CustomException;
import com.example.rest.repository.ProjectRepository;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("ALL")
@Service
public class ProjectService {
    private ProjectRepository projectRepository;
public ResponseEntity<String> insertNewProject(ProjectDTO dto) {
    Project project = new Project();
    project.setId(UUID.randomUUID().toString());
    project.setCreatedAt(dto.getCreatedAt());
    project.setDescription(dto.getDescription());
    project.setTitle(dto.getTitle());

    try {
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.ok(savedProject.getId());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the project: " + e.getMessage());
    }
}
//    public List<Project> getAllProjects() {
//        try {
//            return projectRepository.findAll();
//        } catch (Exception e) {
//            throw new CustomException("An error occurred while fetching all projects");
//        }
//    }
        public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public ResponseEntity<?> getProjectById(String projectId) {
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                return ResponseEntity.ok(project);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Project not found with id: " + projectId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the project");
        }
    }
}