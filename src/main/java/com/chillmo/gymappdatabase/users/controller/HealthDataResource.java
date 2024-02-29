package com.chillmo.gymappdatabase.users.controller;

import com.chillmo.gymappdatabase.users.domain.HealthData;
import com.chillmo.gymappdatabase.users.domain.HealthDataDTO;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.service.HealthDataMapper;
import com.chillmo.gymappdatabase.users.service.UpdateUserDataService;
import com.chillmo.gymappdatabase.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/healthdata")
public class HealthDataResource {

    private final UserService userService;
    private final UpdateUserDataService updateUserDataService;


    /**
     * Adds new health data for a given user.
     * The user is identified within the HealthData object.
     *
     * @param healthData Contains the new health data and the user ID to whom it belongs.
     * @return ResponseEntity with the status of the operation.
     */
    @PostMapping("/add")
    public ResponseEntity<HealthData> addHealthData(@RequestBody HealthDataDTO healthData) {
        System.out.println(healthData.getUserId());

        User currentUser = userService.findUserByID(healthData.getUserId());
        if (currentUser.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        HealthData tmpHealthData = HealthDataMapper.toHealthData(healthData, userService.findUserByID(healthData.getUserId()));

        HealthData addedHealthData = updateUserDataService.addHealthData(currentUser.getId(), tmpHealthData);
        return new ResponseEntity<>(addedHealthData, HttpStatus.CREATED);
    }
}
