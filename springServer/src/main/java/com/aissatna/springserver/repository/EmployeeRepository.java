package com.aissatna.springserver.repository;

import com.aissatna.springserver.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
