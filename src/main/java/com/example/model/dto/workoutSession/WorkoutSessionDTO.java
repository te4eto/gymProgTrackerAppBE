package com.example.model.dto.workoutSession;

import com.example.model.entity.WorkoutSession;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutSessionDTO {
    private Long id;
    private LocalDate date;
    private String type;
    private List<WorkoutSetDTO> sets;

    public WorkoutSessionDTO(WorkoutSession session) {
        this.id = session.getId();
        this.date = session.getDate();
        this.type = session.getType();
        if (session.getSets() != null) {
            this.sets = session.getSets().stream().map(WorkoutSetDTO::new).toList();
        }
    }
}