package tests.demoqabookstore;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    String login = "maxkanzas";
    String password = "8$##dKhiYW4wsCY";

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
    }
    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}
