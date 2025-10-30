package com.example.service;

import com.example.model.dto.workoutSession.WorkoutSessionDTO;
import com.example.model.entity.WorkoutSession;
import com.example.model.entity.WorkoutSet;
import com.example.model.entity.auth.User;
import com.example.repository.UserRepository;
import com.example.repository.WorkoutSessionRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated.");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional(readOnly = true)
    public List<WorkoutSessionDTO> getAll() {
        String currentUsername = getCurrentUsername();

        List<WorkoutSession> sessions = repo.findByUserUsername(currentUsername);

        sessions.forEach(session -> Hibernate.initialize(session.getSets()));
        return sessions.stream().map(WorkoutSessionDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public WorkoutSession create(WorkoutSession session) {
        User currentUser = getCurrentUser();

        session.setUser(currentUser);

        if (currentUser.getSessions() == null) {
            currentUser.setSessions(new ArrayList<>());
        }
        currentUser.getSessions().add(session);


        if (session.getSets() != null) {
            session.getSets().forEach(set -> set.setSession(session));
        }

        return repo.save(session);
    }

    @Transactional(readOnly = true)
    public WorkoutSession getById(Long id) {
        String currentUsername = getCurrentUsername();

        WorkoutSession session = repo.findByIdAndUserUsername(id, currentUsername)
                .orElseThrow(() -> new RuntimeException("Session not found or forbidden"));

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

        updated.setUser(existing.getUser());
        return repo.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        String currentUsername = getCurrentUsername();

        if (!repo.findByIdAndUserUsername(id, currentUsername).isPresent()) {
            throw new RuntimeException("Session not found or forbidden");
        }

        repo.deleteById(id);
    }

    public WorkoutSessionDTO getByDate(LocalDate date) {
        var session =  repo.getByDate(date).orElseThrow();
        return new WorkoutSessionDTO(session);
    }
}
