package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.example.hotelmanagement.model.MealPlan.*;

@Entity
@Getter @Setter
public class Meal {
    @Id
    @GeneratedValue
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

    public double calculateMealsCost(int numberOfDays) {
        double totalCost = 0.00;
        switch (mealPlanName) {
            case ALL_INCLUSIVE -> totalCost += (breakfastPrice + lunchPrice + dinnerPrice) * numberOfDays + 100.00;
            case FULL_BOARD -> totalCost += (breakfastPrice + lunchPrice + dinnerPrice) * numberOfDays;
            case HALF_BOARD -> totalCost += (breakfastPrice + dinnerPrice) * numberOfDays;
            case BED_AND_BREAKFAST -> totalCost += breakfastPrice * numberOfDays;
            default -> totalCost = 0.00;
        }
        return totalCost;
    }
}
