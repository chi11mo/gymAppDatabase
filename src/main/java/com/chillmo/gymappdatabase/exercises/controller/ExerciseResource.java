package com.chillmo.gymappdatabase.exercises.controller;
import com.chillmo.gymappdatabase.exercises.services.ExerciseService;
import com.chillmo.gymappdatabase.exercises.domain.Exercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseResource {
    private final ExerciseService exerciseService;


    public ExerciseResource(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }
    // Add a new exercise
    @PostMapping("/register")
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseService.save(exercise);
        return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
    }

    // Get all exercises
    @GetMapping("/all")
    public List<Exercise> getAllExercises() {
        return exerciseService.findAll();
    }

    // Get an exercise by id
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.findById(id)
                .map(exercise -> ResponseEntity.ok().body(exercise))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an exercise
    @PutMapping("/update")
    public ResponseEntity<Exercise> updateExercise(@RequestBody Exercise exercise) {
        if (exercise.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Exercise updatedExercise = exerciseService.update(exercise);
        return ResponseEntity.ok(updatedExercise);
    }

    // Delete an exercise by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
