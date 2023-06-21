package ru.netologe.data;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netologe.data.DataConstructor.Registration.getRegistratorUser;
import static ru.netologe.data.DataConstructor.Registration.getUser;
import static ru.netologe.data.DataConstructor.getRandomLogin;
import static ru.netologe.data.DataConstructor.getRandomPassword;

public class ServiceTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Success Open Service Registration User")
    void shouldSuccessfulOpenServiceRegistrationUser() {
        var registeredUser = getRegistratorUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(byText("Продолжить")).click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should By Error Not Registration User")
    void shouldBeeErrorNotRegistrationUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $(byText("Продолжить")).click();
        $( "[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Error Open Service Block User")
    void shouldErrorBlockUser() {
        var blockedUser = getRegistratorUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(byText("Продолжить")).click();
        $( "[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Error Open Service Invalid Login")
    void shouldErrorInvalidLogin() {
        var registeredUser = getRegistratorUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(byText("Продолжить")).click();
        $( "[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Error Open Service Invalid Password")
    void shouldErrorInvalidPassword() {
        var registeredUser = getRegistratorUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $(byText("Продолжить")).click();
        $( "[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
