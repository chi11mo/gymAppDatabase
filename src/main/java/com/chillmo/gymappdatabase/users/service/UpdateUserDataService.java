package com.chillmo.gymappdatabase.users.service;

import com.chillmo.gymappdatabase.users.domain.HealthData;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.HealthDataRepository;
import com.chillmo.gymappdatabase.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UpdateUserDataService {
    private final UserRepository userRepository;
    private final HealthDataRepository healthDataRepository;


    /**
     * Adds a new HealthData record to the specified user.
     *
     * @param currentUserId The user to whom the health data should be added.
     * @param newHealthData The new health data record to add.
     * @return The Health data with the updated health data.
     */
    public HealthData addHealthData(Long currentUserId, HealthData newHealthData) {
        // Retrieve the user from the database using their ID to ensure we have the latest version.
        final User userToUpdate = userRepository.findUserById(currentUserId);

        // Check if the user exists
        if (userToUpdate == null) {
            throw new RuntimeException("User not found");
        }
        // Set Created Time.
        newHealthData.setSavedAt(LocalDateTime.now());
        healthDataRepository.save(newHealthData);
        // Associate the new health data with the user. This is crucial for maintaining the relationship.
        newHealthData.setUser(userToUpdate);

        // If the user already has health data records, add the new one to the existing set.
        // Otherwise, initialize a new set and add the health data to it.
        if (userToUpdate.getHealthDataRecords() == null) {
            userToUpdate.setHealthDataRecords(new HashSet<>());
        }
        userToUpdate.getHealthDataRecords().add(newHealthData);

        // Save the updated user entity to the database. This will also persist the new health data
        // thanks to the cascade settings on the user's healthDataRecords field.
        userRepository.save(userToUpdate);

        // Return the updated user
        return newHealthData;
    }

}
