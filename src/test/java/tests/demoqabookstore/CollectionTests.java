package tests.demoqabookstore;

import org.openqa.selenium.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;

public class CollectionTests extends TestBase {
    @Test
    public void addBookToCollectionTest() {
        String authData = "{\"userName\":\"maxkanzas\",\"password\":\"8$##dKhiYW4wsCY\"}";
        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .response();
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + response.path("token"))
                .queryParams("UserId", response.path("userId"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}", response.path("userId") , isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + response.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", response.path("userId").toString()));
        getWebDriver().manage().addCookie(new Cookie("expires", response.path("expires").toString()));
        getWebDriver().manage().addCookie(new Cookie("token", response.path("token").toString()));

        open("/profile");
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
    }
    @Test
    void negative401AddBookToCollectionTest() {
        String userId = "6b11b363-9eed-4a00-9469-51d95bed4ecf";
        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}", userId , isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(401)
                .body("code", is("1200"))
                .body("message", is("User not authorized!"));
    }
}
