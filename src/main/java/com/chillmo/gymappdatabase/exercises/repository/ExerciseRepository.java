package com.chillmo.gymappdatabase.exercises.repository;

import com.chillmo.gymappdatabase.exercises.domain.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository  extends CrudRepository<Exercise,Long> {

}
