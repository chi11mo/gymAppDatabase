package com.chillmo.gymappdatabase.exercises.services;

import com.chillmo.gymappdatabase.exercises.domain.Exercise;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {
    Exercise save(Exercise exercise);

    Optional<Exercise> findById(Long id);

    List<Exercise> findAll();

    Exercise update(Exercise exercise);

    void deleteById(Long id);
}
