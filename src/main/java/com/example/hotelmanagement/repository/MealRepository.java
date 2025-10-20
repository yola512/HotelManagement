package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Meal;
import com.example.hotelmanagement.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    // meals based on meal plan
    List<Meal> findMealByMealPlanName(MealPlan mealPlanName);

    // meals cheaper than selected price
    @Query("SELECT m FROM Meal m WHERE (m.breakfastPrice + m.lunchPrice + m.dinnerPrice) < :price")
    List<Meal> findMealsWithTotalPriceLowerThan(double price);

    // meals with price greater than selected price
    @Query("SELECT m FROM Meal m WHERE (m.breakfastPrice + m.lunchPrice + m.dinnerPrice) > :price")
    List<Meal> findMealsWithTotalPriceGreaterThan(double price);

}
