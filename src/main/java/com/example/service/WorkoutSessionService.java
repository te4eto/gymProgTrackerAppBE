package com.example.service;

import com.example.model.dto.workoutSession.WorkoutSessionDTO;
import com.example.model.entity.WorkoutSession;
import com.example.model.entity.WorkoutSet;
import com.example.repository.WorkoutSessionRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutSessionService {

    @Autowired
    private WorkoutSessionRepository repo;

    @Transactional(readOnly = true)
    public List<WorkoutSessionDTO> getAll() {
        List<WorkoutSession> sessions = repo.findAll();
        sessions.forEach(session -> Hibernate.initialize(session.getSets())); // Ensure sets are loaded
        return sessions.stream().map(WorkoutSessionDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public WorkoutSession create(WorkoutSession session) {
        if (session.getSets() != null) {
            session.getSets().forEach(set -> set.setSession(session));
        }
        return repo.save(session);
    }

    @Transactional(readOnly = true)
    public WorkoutSession getById(Long id) {
        WorkoutSession session = repo.findById(id).orElseThrow(() -> new RuntimeException("Session not found"));
        Hibernate.initialize(session.getSets());
        return session;
    }

    @Transactional
    public WorkoutSession update(Long id, WorkoutSession updated) {
        WorkoutSession existing = getById(id);
        Hibernate.initialize(existing.getSets());
        existing.setDate(updated.getDate());
        existing.setType(updated.getType());
        existing.getSets().clear();
        if (updated.getSets() != null) {
            for (WorkoutSet newSet : updated.getSets()) {
                newSet.setSession(existing);
                existing.getSets().add(newSet);
            }
        }
        return repo.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Session not found");
        }
        repo.deleteById(id);
    }

    public WorkoutSessionDTO getByDate(LocalDate date) {
        var session =  repo.getByDate(date).orElseThrow();
        return new WorkoutSessionDTO(session);
    }
}
