package com.example.rest.mapper;

import com.example.rest.domain.Todo;
import com.example.rest.dto.TodoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
    Todo toEntity(@MappingTarget Todo todo, TodoDTO todoDTO);
}
