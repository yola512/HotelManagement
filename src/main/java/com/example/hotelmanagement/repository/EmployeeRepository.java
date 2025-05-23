package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Employee;
import com.example.hotelmanagement.model.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // find employees by Job Title
    List<Employee> findEmployeeByJobTitle(JobTitle jobTitle);

    // find employees with salary greater than x
    List<Employee> findEmployeeBySalaryGreaterThan(double salary);
    List<Employee> findEmployeeBySalaryLessThan(double salary);



}
