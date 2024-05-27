package com.example.rest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//@SuppressWarnings("ALL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "projects")
public class Project {
    @Id
    private String id;
    private String description;
    private String title;
    @Column(name = "createdat")
    private String createdAt;

    //@GeneratedValue(strategy = GenerationType.IDENTITY)


}
