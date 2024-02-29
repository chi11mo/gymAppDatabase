package com.chillmo.gymappdatabase.users.repository;

import com.chillmo.gymappdatabase.users.domain.HealthData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthDataRepository extends CrudRepository<HealthData, Long> {
    // Additional custom queries if needed
}

