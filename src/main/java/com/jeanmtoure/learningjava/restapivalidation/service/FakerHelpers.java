package com.jeanmtoure.learningjava.restapivalidation.service;

import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import net.datafaker.Faker;

import java.util.Locale;

public final class FakerHelpers {
    private static Faker faker = new Faker(new Locale("fr-FR"));

    public static String generateName() {
        return faker.name().fullName();
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePhoneNumber() {
        return faker.phoneNumber().phoneNumberInternational().replaceAll("\\s+", "");
    }

    public static User.Gender generateGender() {
        return User.Gender.fromCode(faker.gender().shortBinaryTypes().toUpperCase());
    }

    public static int generateAge() {
        return faker.number().numberBetween(18, 60);
    }

    public static String generateNationality() {
        String nationality = nationality = faker.nation().nationality();
        if (nationality.endsWith("s")) {
            nationality = nationality.replaceAll(".$", "");
        }

        return nationality;
    }
}
