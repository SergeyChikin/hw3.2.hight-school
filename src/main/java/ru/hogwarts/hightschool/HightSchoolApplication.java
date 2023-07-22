package ru.hogwarts.hightschool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HightSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HightSchoolApplication.class, args);
	}

}
