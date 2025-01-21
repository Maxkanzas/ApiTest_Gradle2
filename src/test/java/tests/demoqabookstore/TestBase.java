package tests.demoqabookstore;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import models.books.DataUsers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {
    String login = "maxkanzas";
    String password = "8$##dKhiYW4wsCY";

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
    }
    protected void addAuthCookies(DataUsers dataUsers) {
        open("/favicon.ico"); // Инициализация сессии WebDriver
        getWebDriver().manage().addCookie(new Cookie("userID", dataUsers.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", dataUsers.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", dataUsers.getToken()));
    }
    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}
