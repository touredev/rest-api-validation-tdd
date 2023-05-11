package com.jeanmtoure.learningjava.restapivalidation.dto;

import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "Username shouldn't be null")
    private String name;
    @Email(message = "invalid email address")
    private String email;
    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "invalid mobile number entered")
    private String mobile;
    private User.Gender gender;
    @Min(value = 18, message = "must be at least 18 years old")
    @Max(value = 60, message = "must be at most 60 years old")
    private int age;
    @NotBlank(message = "Nationality shouldn't be blank")
    private String nationality;
}
