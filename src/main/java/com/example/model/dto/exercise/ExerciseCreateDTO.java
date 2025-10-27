package com.example.model.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExerciseCreateDTO {
    private String name;

    private String category;
}
