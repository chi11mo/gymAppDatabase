package com.chillmo.gymappdatabase.users.service;


import com.chillmo.gymappdatabase.users.domain.HealthData;
import com.chillmo.gymappdatabase.users.domain.HealthDataDTO;
import com.chillmo.gymappdatabase.users.domain.User;


public class HealthDataMapper {


    /**
     * Converts a HealthData entity to a HealthDataDTO.
     *
     * @param healthData the HealthData entity to convert
     * @return a HealthDataDTO with data copied from the HealthData entity
     */
    public static HealthDataDTO toDTO(HealthData healthData) {
        if (healthData == null) {
            return null;
        }

        HealthDataDTO dto = new HealthDataDTO();
        dto.setId(healthData.getId());
        dto.setHeight(healthData.getHeight());
        dto.setWeight(healthData.getWeight());
        dto.setBodyFatPercentage(healthData.getBodyFatPercentage());
        dto.setUserId(healthData.getUser().getId()); // Only set the user ID for the DTO
        return dto;
    }

    /**
     * Converts a HealthDataDTO to a HealthData entity.
     * Note: This method does not handle setting the User entity in the HealthData object.
     * The User entity should be set separately based on the context in which this method is used.
     *
     * @param dto the HealthDataDTO to convert
     * @return a new HealthData entity with data copied from the HealthDataDTO
     */
    public static HealthData toHealthData(HealthDataDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        HealthData healthData = new HealthData();
        healthData.setId(dto.getId());
        healthData.setHeight(dto.getHeight());
        healthData.setWeight(dto.getWeight());
        healthData.setBodyFatPercentage(dto.getBodyFatPercentage());
        healthData.setUser(user);
        return healthData;
    }

    // Constructors, getters, and setters
}
