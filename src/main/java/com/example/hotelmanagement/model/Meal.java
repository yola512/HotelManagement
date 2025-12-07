package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private MealPlan mealPlanName;

    private String description; // meal plan description
    private double breakfastPrice;
    private double lunchPrice;
    private double dinnerPrice;

    public Meal() {}

    public Meal(MealPlan mealPlanName, String description, double breakfastPrice, double lunchPrice,
                double dinnerPrice) {
        this.mealPlanName = mealPlanName;
        this.description = description;
        this.breakfastPrice = breakfastPrice;
        this.lunchPrice = lunchPrice;
        this.dinnerPrice = dinnerPrice;
    }
}
