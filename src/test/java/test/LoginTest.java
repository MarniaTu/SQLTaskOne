package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;


import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.deleteDataFromAuthCodes;
import static data.SQLHelper.deleteDataFromTables;

public class LoginTest {
    LoginPage loginPage;
    VerificationPage verificationPage;
    DashboardPage dashboardPage;


    @AfterEach
    void tearDown() {
        deleteDataFromAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        deleteDataFromTables();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldSuccessfullyLogin() {
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageIsVisible();
        var verificationCode = SQLHelper.getValidVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode.getVerificationCode());
        dashboardPage.verifyDashboardPageIsVisible();

    }

    @Test
    void shouldGetErrorMessageIfUserNotAddedToDataBase() {
        var authInfo = DataHelper.getNewUser();
        loginPage.validLogin(authInfo);
        loginPage.getErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    //
    @Test
    void shouldGetErrorMessageIfInvalidVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageIsVisible();
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.verify(verificationCode.getVerificationCode());
        verificationPage.getErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}
