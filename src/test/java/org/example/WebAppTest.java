package org.example;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {
    @BeforeEach
    public void setUp() {
        open("http://51.250.6.164:3000/signin");
    }

    @Test
    public void incorrectLogin() {
        LoginPage loginpage = new LoginPage();
        loginpage.insertLogin("fndtnbd");
        loginpage.insertPassword("bfxdfv xf");
        loginpage.clickLogin();
        loginpage.checkErrorPopup();

    }

    @Test
    public void correctLogin() {
        Configuration.headless = true;
        LoginPage loginpage = new LoginPage();
        loginpage.insertLogin("antonf");
        loginpage.insertPassword("hellouser123");
        loginpage.clickLogin();
        loginpage.checkSuccessfulLogin();

    }

    @AfterEach
    public void setDown() {
        closeWebDriver();
    }

}
