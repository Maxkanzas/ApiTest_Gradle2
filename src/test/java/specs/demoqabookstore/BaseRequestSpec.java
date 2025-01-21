package specs.demoqabookstore;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class BaseRequestSpec {
    public static RequestSpecification bookRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().method()
            .contentType(JSON)
            .baseUri("https://demoqa.com")
            .basePath("/Account/v1/Login");
    public static RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api/login");
    public static RequestSpecification listUsersRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api/users?page=2");
}
