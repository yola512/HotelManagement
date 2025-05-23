package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Meal;
import com.example.hotelmanagement.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    // meals based on meal plan
    List<Meal> findMealByMealPlan(MealPlan mealPlan);

    // meals containing specific dietary options
    List<Meal> findMealByDietaryOptionsContainingIgnoreCase(String dietaryOptions);

    // meals cheaper than selected price
    List<Meal> findMealByPriceLessThan(double price);
    // meals with price greater than selected price
    List<Meal> findMealByPriceGreaterThan(double price);

}
