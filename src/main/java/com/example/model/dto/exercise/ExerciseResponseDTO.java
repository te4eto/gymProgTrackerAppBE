package com.example.model.dto.exercise;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExerciseResponseDTO {
    private Long id;
    private String name;
    private String category;
}
