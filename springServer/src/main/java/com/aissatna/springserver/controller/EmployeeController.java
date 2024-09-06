package com.aissatna.springserver.controller;

import com.aissatna.springserver.model.Employee;
import com.aissatna.springserver.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        log.info("Employee with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        employeeRepository.findById(id).ifPresent(e -> {
            e.setFirstName(employee.getFirstName());
            e.setLastName(employee.getLastName());
            e.setEmail(employee.getEmail());
            employeeRepository.save(e);
            log.info("Employee with id {} updated", id);
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        Employee e = employeeRepository.save(employee);
        log.info("Employee with id {} created", e.getId());
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

}
