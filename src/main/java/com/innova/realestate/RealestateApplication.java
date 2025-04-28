package com.innova.realestate;

import com.innova.realestate.models.Address;
import com.innova.realestate.models.Property;
import com.innova.realestate.models.User;
import com.innova.realestate.repositories.PropertyRepository;
import com.innova.realestate.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class RealestateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealestateApplication.class, args);
	}


}
