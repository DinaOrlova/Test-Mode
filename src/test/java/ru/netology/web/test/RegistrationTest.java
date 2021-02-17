package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.RegistrationInfo;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestActiveUser() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateUser("active");
        DataGenerator.UserCreation.setUpAll(registrationInfo);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestBlockedUser() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateUser("blocked");
        DataGenerator.UserCreation.setUpAll(registrationInfo);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorWhenInvalidLogin() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateUser("active");
        RegistrationInfo registrationInvalidInfo = DataGenerator.Registration.generateUser("active");
        DataGenerator.UserCreation.setUpAll(registrationInfo);
        $("[data-test-id=login] input").setValue(registrationInvalidInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldGetErrorWhenInvalidPassword() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateUser("active");
        RegistrationInfo registrationInvalidInfo = DataGenerator.Registration.generateUser("active");
        DataGenerator.UserCreation.setUpAll(registrationInfo);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInvalidInfo.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
