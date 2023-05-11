package com.jeanmtoure.learningjava.restapivalidation.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Entity
@Table(name = "USERS_TBL")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {

    public enum Gender {
        MALE('M'),
        FEMALE('F');

        private char code;
        private Gender(char code) {
            this.code = code;
        }

        @JsonCreator
        public static Gender fromCode(String code) {
            return Stream.of(Gender.values())
                    .filter(targetEnum -> String.valueOf(targetEnum.code).equals(code))
                    .findFirst().orElse(null);
        }

        @JsonValue
        public String getCode() {
            return String.valueOf(code);
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String mobile;
    private Gender gender;
    private int age;
    private String nationality;

}
