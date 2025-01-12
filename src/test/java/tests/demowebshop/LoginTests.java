package tests.demowebshop;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginTests extends TestBase {
    @Test
    public void loginUITest() {
        step("Открыть страницу авторизации", () -> {
            open("/login");
        });
        step("Вводим логин и пароль", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();
        });
        step("Проверяем фактическую авторизацию", () -> {
            $(".account").shouldHave(text(login));
        });
    }
    @Test
    public void loginApiTest() {
        step("Получаем cookie авторизации", () -> {
            String authCookieKey = "NOPCOMMERCE.AUTH";
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Password", password)
                    .formParam("Email", login)
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract()
                    .cookie(authCookieKey);
            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(authCookieKey, authCookieValue);
            getWebDriver().manage().addCookie(authCookie);
        });
        step("Открываем главную страницу", () ->
                open(""));
        step("Проверяем фактическую авторизацию", () -> {
            $(".account").shouldHave(text(login));
        });
    }
}
