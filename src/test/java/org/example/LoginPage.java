package org.example;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void insertLogin(String query) {
        $(By.xpath("//input[@data-name=\"username-input\"]")).setValue(query);
    }

    public void insertPassword(String query) {
        $(By.xpath("//input[@data-name=\"password-input\"]")).setValue(query);
    }

    public void checkSuccessfulLogin() {
        $(By.xpath("//input[@data-name=\"comment-input\"]"))
                .shouldBe(Condition.visible);
    }

    public void clickLogin() {
        $(By.xpath("//button[@data-name=\"signIn-button\"]")).click();
    }

    public void checkErrorPopup() {
        $(By.xpath("//div[@data-name=\"authorizationError-popup\"]"))
                .shouldBe(Condition.visible);
    }
}
