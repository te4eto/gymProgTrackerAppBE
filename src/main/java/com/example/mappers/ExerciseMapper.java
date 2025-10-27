package com.example.mappers;

import com.example.model.dto.exercise.ExerciseCreateDTO;
import com.example.model.dto.exercise.ExerciseResponseDTO;
import com.example.model.entity.Exercise;

public class ExerciseMapper {
    public static Exercise toEntity(ExerciseCreateDTO dto) {
        return Exercise.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .build();
    }


    public static ExerciseResponseDTO toResponseDTO(Exercise entity) {
        return ExerciseResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .build();
    }
}
