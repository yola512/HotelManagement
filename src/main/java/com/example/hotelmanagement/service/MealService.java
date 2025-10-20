package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Meal;
import com.example.hotelmanagement.model.MealPlan;
import com.example.hotelmanagement.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> getMealById(int id) {
        return mealRepository.findById(id);
    }

    public List<Meal> getMealsByMealPlanName(MealPlan plan) {
        return mealRepository.findMealByMealPlanName(plan);
    }

    public List<Meal> getMealsCheaperThan(double price) {
        return mealRepository.findMealsWithTotalPriceLowerThan(price);
    }

    public List<Meal> getMealsMoreExpensiveThan(double price) {
        return mealRepository.findMealsWithTotalPriceGreaterThan(price);
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(int id, Meal updatedMeal) {
        return mealRepository.findById(id)
                .map(existingMeal -> {
                    existingMeal.setMealPlanName(updatedMeal.getMealPlanName());
                    existingMeal.setDescription(updatedMeal.getDescription());
                    existingMeal.setBreakfastPrice(updatedMeal.getBreakfastPrice());
                    existingMeal.setLunchPrice(updatedMeal.getLunchPrice());
                    existingMeal.setDinnerPrice(updatedMeal.getDinnerPrice());

                    return mealRepository.save(existingMeal);
                })
                .orElseThrow(() -> new RuntimeException("Meal with ID " + id + " not found"));
    }

    public void deleteMeal(int id) {
        mealRepository.deleteById(id);
    }
}