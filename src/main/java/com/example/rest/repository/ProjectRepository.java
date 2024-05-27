package com.example.rest.repository;

import com.example.rest.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface ProjectRepository extends JpaRepository<Project, String> {

}
