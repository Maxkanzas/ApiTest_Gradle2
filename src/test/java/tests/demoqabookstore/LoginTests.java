package tests.demoqabookstore;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginTests extends TestBase {
    @Test
    public void successFullUILoginTest() {
        step("Открыть страницу авторизации", ()->{
            open("/login");
        });
        step("Вводим логин и пароль", ()-> {
            $("[placeholder='UserName']").setValue(login);
            $("[placeholder='Password']").setValue(password);
            $("#login").click();
        });
        step("Проверка авторизации", ()-> {
            $("#userName-value").shouldHave(text(login));
        });
    }
    @Test
    public void successFullLoginApiTest() {
        String authData = "{\"userName\":\"maxkanzas\",\"password\":\"8$##dKhiYW4wsCY\"}";
        step("Авторизация на сайте", ()->{
            Response authResponse = given()
                    .log().uri()
                    .log().method()
                    .log().body()
                    .contentType("application/json")
                    .body(authData)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract()
                    .response();
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
        });
        step("Проверка авторизации", ()-> {
            open("/profile");
            $("#userName-value").shouldHave(text("maxkanzas"));
        });

    }
}
