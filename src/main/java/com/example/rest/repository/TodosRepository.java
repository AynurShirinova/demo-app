package com.example.rest.repository;

import com.example.rest.domain.Todo;
import com.example.rest.dto.BetweenDateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodosRepository extends JpaRepository<Todo, String> {

        Optional<Todo> findByAssignedTo(String id);

        @Query("SELECT t FROM Todo t WHERE t.created BETWEEN :startDate AND :endDate")
        List<Todo> getTodosBetweenDates(LocalDate startDate, LocalDate endDate);

}
