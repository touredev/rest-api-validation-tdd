package com.jeanmtoure.learningjava.restapivalidation;

import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import com.jeanmtoure.learningjava.restapivalidation.repository.UserRepository;
import com.jeanmtoure.learningjava.restapivalidation.service.FakerHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RestApiValidationApplication {


    static final Logger log =
            LoggerFactory.getLogger(RestApiValidationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApiValidationApplication.class, args);
    }

    @Bean
    CommandLineRunner init (UserRepository userRepository){
        return args -> {
            log.info("Database initializing with faker plugin data...");
            List<User> userList = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                userList.add(User.build(
                        0,
                        FakerHelpers.generateName(),
                        FakerHelpers.generateEmail(),
                        FakerHelpers.generatePhoneNumber(),
                        FakerHelpers.generateGender(),
                        FakerHelpers.generateAge(),
                        FakerHelpers.generateNationality()));
            }
            userRepository.saveAll(userList);
            log.info("Database initialized with fake data.");
        };
    }

}
