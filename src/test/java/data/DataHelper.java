package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String getNewLogin() {
        return faker.name().username();
    }

    private static String getNewPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getNewUser() {
        return new AuthInfo(getNewLogin(), getNewPassword());
    }

    @Value
    public static class VerificationCode {
        String verificationCode;
    }

    public static VerificationCode getRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

}
