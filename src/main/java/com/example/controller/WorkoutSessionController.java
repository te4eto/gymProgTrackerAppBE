package com.example.controller;


import com.example.model.dto.workoutSession.WorkoutSessionDTO;
import com.example.model.entity.WorkoutSession;
import com.example.service.WorkoutSessionService;
import com.example.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/sessions")
public class WorkoutSessionController {

    @Autowired
    private WorkoutSessionService service;

    @GetMapping
    public ApiResponse<List<WorkoutSessionDTO>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @PostMapping
    public ApiResponse<WorkoutSession> create(@RequestBody WorkoutSession session) {
        return ApiResponse.ok(service.create(session));
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkoutSession> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkoutSession> update(@PathVariable Long id, @RequestBody WorkoutSession session) {
        return ApiResponse.ok(service.update(id, session));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/by-date/{date}")
    public ApiResponse<WorkoutSessionDTO> getByDate(@PathVariable String date) {
        return ApiResponse.ok(service.getByDate(LocalDate.parse(date)));
    }
}