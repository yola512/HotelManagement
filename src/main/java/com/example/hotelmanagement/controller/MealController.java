package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.Meal;
import com.example.hotelmanagement.model.MealPlan;
import com.example.hotelmanagement.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // BUSINESS QUERIES
    @GetMapping("/plan/{plan}")
    public List<Meal> getMealsByMealPlanName(@PathVariable MealPlan plan) {
        return mealService.getMealsByMealPlanName(plan);
    }

    @GetMapping("/cheaper-than")
    public List<Meal> getMealsCheaperThan(@RequestParam double price) {
        return mealService.getMealsCheaperThan(price);
    }

    @GetMapping("/more-expensive-than")
    public List<Meal> getMealsMoreExpensiveThan(@RequestParam double price) {
        return mealService.getMealsMoreExpensiveThan(price);
    }

    // CRUD
    // Get all meals
    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    // Get meal by ID
    @GetMapping("/{id}")
    public Optional<Meal> getMealById(@PathVariable int id) {
        return mealService.getMealById(id);
    }


    // create a new meal (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Meal createMeal(@RequestBody Meal meal) {
        return mealService.createMeal(meal);
    }

    // update meal (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable int id, @RequestBody Meal meal) {
        try {
            return ResponseEntity.ok(mealService.updateMeal(id, meal));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // delete meal (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable int id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }


}