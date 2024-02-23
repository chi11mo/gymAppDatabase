package com.chillmo.gymappdatabase.exercises.services;

import com.chillmo.gymappdatabase.exercises.domain.Exercise;
import com.chillmo.gymappdatabase.exercises.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ExerciseService implements IExerciseService {
    @Autowired
    private final ExerciseRepository exerciseRepository;



    @Override
    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public List<Exercise> findAll() {
        return (List<Exercise>) exerciseRepository.findAll();
    }

    @Override
    public Exercise update(Exercise exercise) {
        // Ensure the exercise exists before updating
        if (exercise.getId() != null && exerciseRepository.existsById(exercise.getId())) {
            return exerciseRepository.save(exercise);
        }
        return null; // Or throw an exception based on your error handling policy
    }

    @Override
    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }
}
