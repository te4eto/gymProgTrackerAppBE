package com.example.repository;

import com.example.model.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    public Optional<WorkoutSession> getByDate(LocalDate date);
    List<WorkoutSession> findByUserUsername(String username);
    Optional<WorkoutSession> findByIdAndUserUsername(Long id, String username);
}