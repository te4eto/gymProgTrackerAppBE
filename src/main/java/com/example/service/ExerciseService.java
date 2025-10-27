package com.example.service;

import com.example.mappers.ExerciseMapper;
import com.example.model.dto.exercise.ExerciseCreateDTO;
import com.example.model.dto.exercise.ExerciseResponseDTO;
import com.example.model.entity.Exercise;
import com.example.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<ExerciseResponseDTO> getAll() {
        return exerciseRepository.findAll().stream()
                .map(ExerciseMapper::toResponseDTO)
                .toList();
    }

    public ExerciseResponseDTO create(ExerciseCreateDTO dto) {
        if(exerciseRepository.findByName(dto.getName()).isPresent()){
            throw new ExerciseExistsException(dto.getName());
        }

        Exercise newExercise = ExerciseMapper.toEntity(dto);
        Exercise savedExercise = exerciseRepository.save(newExercise);

        return ExerciseMapper.toResponseDTO(savedExercise);
    }

    public ExerciseResponseDTO getById(Long id) {
        Exercise entity = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        return ExerciseMapper.toResponseDTO(entity);
    }

    public boolean deleteById(Long id) {
        if (!exerciseRepository.existsById(id)) {
            return false;
        }

        exerciseRepository.deleteById(id);
        return true;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public class ExerciseExistsException extends RuntimeException {
        public ExerciseExistsException(String name) {
            super("Exercise with name " + name + " already exists");
        }
    }
}


