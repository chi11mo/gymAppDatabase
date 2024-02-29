package com.chillmo.gymappdatabase.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents health-related data for a user.
 * This class is mapped to a database table 'health_data' to store health metrics.
 */
@Entity
@Setter
@Getter
@Table(name = "health_data")
public class HealthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private double bodyFatPercentage;

    @Column
    private int steps;

    @Column
    private LocalDateTime savedAt;

    // ManyToOne relationship indicating many health data records can belong to one user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Specifies the foreign key column.
    @JsonIgnore
    private User user;

    /**
     * Default constructor for JPA.
     */
    public HealthData() {
    }

    /**
     * Parametrized constructor to create a HealthData object with all fields initialized.
     *
     * @param height            the height of the user in meters.
     * @param weight            the weight of the user in kilograms.
     * @param bodyFatPercentage the body fat percentage of the user.
     * @param steps             the number of steps taken by the user.
     * @param savedAt           the date when data is saved.
     * @param user              the associated User object this health data belongs to.
     */
    public HealthData(double height, double weight, double bodyFatPercentage, int steps, LocalDateTime savedAt, User user) {
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.steps = steps;
        this.savedAt = savedAt;
        this.user = user;
    }

    // Getters and Setters
}
