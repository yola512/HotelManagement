package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Employee;
import com.example.hotelmanagement.model.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // find employees with salary greater than x
    List<Employee> findEmployeeBySalaryGreaterThan(double salary);
    List<Employee> findEmployeeBySalaryLessThan(double salary);



}
