package com.example.repository;

import com.example.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    public Optional<Exercise> findByName(String name);
}
