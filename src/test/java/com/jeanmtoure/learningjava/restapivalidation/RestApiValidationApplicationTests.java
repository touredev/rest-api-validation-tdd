package com.jeanmtoure.learningjava.restapivalidation;

import com.jeanmtoure.learningjava.restapivalidation.dto.UserRequest;
import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import com.jeanmtoure.learningjava.restapivalidation.repository.UserRepository;
import com.jeanmtoure.learningjava.restapivalidation.service.FakerHelpers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.h2.console.enabled=true")
@ActiveProfiles("test")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(statements = "TRUNCATE TABLE USERS_TBL", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RestApiValidationApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/users");
    }


    @Test
    public void testAddUser() {
        baseUrl = baseUrl.concat("/register");

        UserRequest request = new UserRequest(
                FakerHelpers.generateName(),
                FakerHelpers.generateEmail(),
                FakerHelpers.generatePhoneNumber(),
                FakerHelpers.generateGender(),
                FakerHelpers.generateAge(),
                FakerHelpers.generateNationality());
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, request, User.class);
        assertEquals(request.getName(), response.getBody().getName());
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    public void testGetAllUsers() {
        baseUrl = baseUrl.concat("/all");
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            userList.add(User.build(0,
                    FakerHelpers.generateName(),
                    FakerHelpers.generateEmail(),
                    FakerHelpers.generatePhoneNumber(),
                    FakerHelpers.generateGender(),
                    FakerHelpers.generateAge(),
                    FakerHelpers.generateNationality()));
        }
        userRepository.saveAll(userList);

        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        assertEquals(5, response.getBody().size());
        assertEquals(5, userRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO USERS_TBL (id, name, email, mobile, gender, age, nationality) VALUES (1, 'Jean', 'egomagis@gmail.com', '+336001220077', 0, 29, 'Ivorian')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetUser() {
        baseUrl = baseUrl.concat("/{id}");
        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl, User.class, 1);
        User user = response.getBody();
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(1, user.getId()),
                () -> assertEquals("Jean", user.getName())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO USERS_TBL (id, name, email, mobile, gender, age, nationality) VALUES (1, 'Alida', 'alida@gmail.com', '+337998779922', 1, 26, 'Ivorian')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateUser() {
        baseUrl = baseUrl.concat("/update/{id}");
        UserRequest request = new UserRequest(
                FakerHelpers.generateName(),
                FakerHelpers.generateEmail(),
                FakerHelpers.generatePhoneNumber(),
                FakerHelpers.generateGender(),
                FakerHelpers.generateAge(),
                FakerHelpers.generateNationality());
        restTemplate.put(baseUrl, request, 1);
        User user = userRepository.findById(1);
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(request.getName(), user.getName()),
                () -> assertEquals(request.getEmail(), user.getEmail())
        );
    }
}
