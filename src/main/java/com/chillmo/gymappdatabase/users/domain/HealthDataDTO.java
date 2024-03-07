package com.chillmo.gymappdatabase.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HealthDataDTO {
    private long id;
    private double weight;
    private double height;
    private double bodyFatPercentage;
    private long userId; // Only include the user ID

    public HealthDataDTO() {

    }


}
