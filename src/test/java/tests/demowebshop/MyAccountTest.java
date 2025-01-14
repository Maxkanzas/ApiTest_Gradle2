package tests.demowebshop;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class MyAccountTest extends TestBase { @Test
public void changeFirstNameTest() {
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
    String data = "vcZPsNiBX8qWaMeyfrk2BRw-MSOy8DHsT5KwyyjxWzli5wYyfHc8yJcZlSA7ccMrPz-Qpb6iJOqy9fN49PhWjTzzNkBWTYrwNWDGsUyqpPVFqI8UBbMJaNVeY5Q6ZYsm0";

    given()
            .contentType("application/x-www-form-urlencoded")
            .cookie(authCookieKey, authCookieValue)
            .body(data)
            .when()
            .post("/customer/info")
            .then()
            .log().all()
            .statusCode(302);
}
}
