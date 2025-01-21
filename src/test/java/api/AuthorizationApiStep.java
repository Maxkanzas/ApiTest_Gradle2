package api;

import models.books.Credentials;
import models.books.DataUsers;

import static io.restassured.RestAssured.given;
import static specs.demoqabookstore.BaseRequestSpec.bookRequestSpec;
import static specs.demoqabookstore.BaseResponseSpec.baseResponseSpec;

public class AuthorizationApiStep {
    public DataUsers loginAuthorization (Credentials credentials) {
        return given(bookRequestSpec)
                .body(credentials)
                .when()
                .post()
                .then()
                .spec(baseResponseSpec)
                .extract().as(DataUsers.class);
    }
}
