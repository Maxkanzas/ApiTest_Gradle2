package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTestsPojo {
    String authFullData = "{\n" + "\"email\": \"eve.holt@reqres.in\",\n" + "\"password\": \"cityslicka\"\n" + "}";
    String authDataNotPassword = "{\n" + "\"email\": \"peter@klaven\"\n" + "}";
    String authDataAnotherPassword = "{\n" + "\"email\": \"eve.holt@reqres.in\",\n" + "\"password\": \"pistol\"\n" + "}";
    String authDataNotPassword1 = "{\n" + "\"email\": \"sydney@fife\"\n" + "}";
    String authDataCreateUser = "{\n" + "\"name\": \"morpheus\",\n" + "\"job\": \"leader\"\n" + "}";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void loginSuccessFullTest() {
        given()
                .body(authFullData)
                .contentType(JSON)
                .log().all()

                .when()
                .post("/login")

                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void registerSuccessfulTest() {
        given()
                .body(authDataAnotherPassword)
                .contentType(JSON)
                .log().all()

                .when()
                .post("/register")

                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .body("id", is(4));
    }

    @Test
    public void createUserTest() {
        given()
                .body(authDataCreateUser)
                .contentType(JSON)
                .log().all()

                .when()
                .post("/users")

                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void getUsersListTest() {
        given()
                .log().all()
                .when()
                .get("/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data.size()", is(6))
                .body("data[0].id", is(7))
                .body("data[0].email", is("michael.lawson@reqres.in"))
                .body("data[0].first_name", is("Michael"))
                .body("data[0].last_name", is("Lawson"))
                .body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"))
                .body("support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
                .body("support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    public void unSuccessFullLoginTest() {
        given()
                .body(authDataNotPassword)
                .contentType(JSON)
                .log().all()

                .when()
                .post("/login")

                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void registerUnSuccessFullTest() {
        given()
                .body(authDataNotPassword1)
                .contentType(JSON)
                .log().all()

                .when()
                .post("/register")

                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
