package org.skidrow.logcleanupexamples;

import net.datafaker.Faker;

import java.util.Random;

public class PiiDataGenerator {

    private static final Random random = new Random();

    public static String generateRandomData() {
        return new Faker().internet().emailAddress();
    }

    public static String morePii() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String ip = faker.internet().ipV4Address();
        String phone = faker.phoneNumber().cellPhone();
        String international = faker.phoneNumber().phoneNumberInternational();
        String nationalNumber = faker.phoneNumber().phoneNumberNational();

        return "Email: " + email +
                ", IP: " + ip +
                ", Phone: " + phone +
                ", International: " + international +
                ", National: " + nationalNumber;
    }
}
