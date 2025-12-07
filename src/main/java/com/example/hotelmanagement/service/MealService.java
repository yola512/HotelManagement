package com.example.hotelmanagement.service;

import com.example.hotelmanagement.exceptionHandler.DeleteException;
import com.example.hotelmanagement.model.Booking;
import com.example.hotelmanagement.model.Meal;
import com.example.hotelmanagement.model.MealPlan;
import com.example.hotelmanagement.repository.BookingRepository;
import com.example.hotelmanagement.repository.MealRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final BookingRepository bookingRepository;

    public MealService(MealRepository mealRepository, BookingRepository bookingRepository) {
        this.mealRepository = mealRepository;
        this.bookingRepository = bookingRepository;
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

    @Transactional
    public void deleteMeal(int id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal with ID " + id + " not found"));

        // get every booking using this meal
        List<Booking> bookingsWithThisMeal = bookingRepository.findByMeal(meal);

        if (!bookingsWithThisMeal.isEmpty()) {
            // if meal is used in booking admin CAN'T delete it unless the booking is deleted -> throw exception and give admin info
            throw new DeleteException(
                    "Cannot delete meal – it's used in "
                            + bookingsWithThisMeal.size() + " booking(s)."
            );
        }

        mealRepository.deleteById(id);
    }
}