package org.example;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import helpers.SetupFunctions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {
    SetupFunctions setupFunctions = new SetupFunctions();
    String baseURL = setupFunctions.getBaseUrl();
    String login = setupFunctions.getLogin();
    String password = setupFunctions.getPassword();

    @BeforeEach
    public void setUp() {
        open(baseURL);
    }

    @Test
    public void incorrectLogin() {
        String randomName = setupFunctions.generateRandomName();
        String randompassword = setupFunctions.generateRandomPassword();
        LoginPage loginpage = new LoginPage();
        loginpage.insertLogin(randomName);
        loginpage.insertPassword(randompassword);
        loginpage.clickLogin();
        loginpage.checkErrorPopup();

    }

    @Test
    public void correctLogin() {
        Configuration.headless = true;
        LoginPage loginpage = new LoginPage();
        loginpage.insertLogin(login);
        loginpage.insertPassword(password);
        loginpage.clickLogin();
        loginpage.checkSuccessfulLogin();

    }

    @AfterEach
    public void setDown() {
        closeWebDriver();
    }

}
