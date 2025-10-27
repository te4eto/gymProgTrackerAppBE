package com.example.controller;


import com.example.model.dto.exercise.ExerciseCreateDTO;
import com.example.model.dto.exercise.ExerciseResponseDTO;
import com.example.service.ExerciseService;
import com.example.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ApiResponse<List<ExerciseResponseDTO>> getAll() {
        return ApiResponse.ok(exerciseService.getAll());
    }

    @PostMapping
    public ApiResponse<ExerciseResponseDTO> create(@RequestBody ExerciseCreateDTO dto) {
        return ApiResponse.ok(exerciseService.create(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExerciseResponseDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(exerciseService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteExercise(@PathVariable Long id) {
        boolean deleted = exerciseService.deleteById(id);

        if (deleted) {
            return ApiResponse.ok(null);
        } else {
            return ApiResponse.fail("Exercise not found");
        }
    }
}
