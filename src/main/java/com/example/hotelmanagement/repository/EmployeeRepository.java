package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // find employees with salary greater/smaller than x
    List<Employee> findEmployeeBySalaryGreaterThan(double salary);
    List<Employee> findEmployeeBySalaryLessThan(double salary);

    // used in reports.html; function fetchBookingsOverTimeChart()
    // Object[0] - JobTitle, Object[1] - count
    @Query("SELECT e.jobTitle, COUNT(e) FROM Employee e GROUP BY e.jobTitle ORDER BY COUNT(e) DESC")
    List<Object[]> countEmployeesByJobTitle();
}
