package com.example.model.dto.workoutSession;

import com.example.model.entity.WorkoutSet;
import lombok.Data;

@Data
public class WorkoutSetDTO {
    private Long id;
    private int reps;
    private double weight;
    private Long exerciseId;

    public WorkoutSetDTO(WorkoutSet set) {
        this.id = set.getId();
        this.reps = set.getReps();
        this.weight = set.getWeight();
        if (set.getExercise() != null) {
            this.exerciseId = set.getExercise().getId();
        }
    }
}