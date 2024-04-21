package com.blogapplication;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplicationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationsApplication.class, args);
	}
	//using model mapper to convert DTO.
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

}
