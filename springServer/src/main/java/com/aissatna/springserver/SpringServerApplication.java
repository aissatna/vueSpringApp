package com.aissatna.springserver;

import com.aissatna.springserver.model.Employee;
import com.aissatna.springserver.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class SpringServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringServerApplication.class, args);
	}

	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception {
		Employee e1 = Employee.builder()
				.firstName("John")
				.lastName("Deep")
				.email("johndeep@email.com")
				.build();

		Employee e2 = Employee.builder()
				.firstName("Tony")
				.lastName("Stark")
				.email("tonystark@email.com")
				.build();

		Employee e3 = Employee.builder()
				.firstName("John")
				.lastName("Cena")
				.email("johncena@email.com")
				.build();

		Employee e4 = Employee.builder()
				.firstName("Leo")
				.lastName("Messi")
				.email("leomessi@email.com")
				.build();

		employeeRepository.saveAll(List.of(e1, e2, e3, e4));

	}
}
