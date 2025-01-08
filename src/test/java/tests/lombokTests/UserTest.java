package tests.lombokTests;

import models.lombok.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.ListUsersSpec.listUsersRequestSpec;
import static specs.LoginSpec.*;
import static specs.MissingPasswordResponseSpec.missingPasswordResponseSpec;
import static specs.RequestSpec.loginRequestSpecRegister;
import static specs.ResponseSpec.loginResponseSpec;

@Tag("RegTests")
public class UserTest {

    @Test
    public void loginSuccessFullLombokTest() {
        DataLombok authData = new DataLombok();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        TokenLombok response = step("Направили запрос", () ->
                given(loginRequestSpec)
                        .body(authData)
                .when()
                        .post()

                .then()
                        .spec(loginResponseSpec)
                        .extract().body().as(TokenLombok.class));
        step("Проверили ответ", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    public void registerSuccessfulLombokTest() {
        DataLombok authData = new DataLombok();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponse response = step("Отправка запроса и извлечение ответа", () ->
                given(loginRequestSpecRegister)
                        .body(authData)
                .when()
                        .post()
                .then()
                        .spec(loginResponseSpec)
                        .extract().body().as(RegisterResponse.class));

        step("Проверка ответа", () -> {
            assertNotNull(response.getId(), "Поле id отсутствует в ответе");
            assertNotNull(response.getToken(), "Поле token отсутствует в ответе");
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }
    @Test
    public void unSuccessFullLoginLombokTest() {
        DataLombok authData = new DataLombok();
        authData.setEmail("peter@klaven");

        ErrorLombok response = step("Отправка запроса и извлечение ответа", () ->
            given(loginRequestSpec)
                    .body(authData)

            .when()
                    .post()

            .then()
                    .spec(missingPasswordResponseSpec)
                    .extract().body().as(ErrorLombok.class));
        step("Проверили ответ", () ->
                assertEquals("Missing password", response.getError()));
    }
    @Test
    public void registerUnSuccessFullLombokTest() {
        DataLombok authData = new DataLombok();
        authData.setEmail("sydney@fife");

        ErrorLombok response = step("Отправка запроса и извлечение ответа", () ->
            given(loginRequestSpecRegister)
                    .body(authData)

            .when()
                    .post()

            .then()
                    .spec(missingPasswordResponseSpec)
                    .extract().body().as(ErrorLombok.class));
        step("Проверили ответ", () ->
                assertEquals("Missing password", response.getError()));
    }
    @Test
    public void ListUsersLombokTest() {
        ListUsersLombok response = step("Направили запрос", () ->
                given(listUsersRequestSpec)
                .when()
                        .get()

                .then()
                        .spec(loginResponseSpec)
                        .extract().body().as(ListUsersLombok.class));
        step("Проверили параметры ответа", () -> {

            assertNotNull(response.getPage(), "Missing 'page'");
            assertNotNull(response.getPer_page(), "Missing 'per_page'");
            assertNotNull(response.getTotal(), "Missing 'total'");
            assertNotNull(response.getTotal_pages(), "Missing 'total_pages'");

            assertEquals(1, response.getPage(), "'page' mismatch");
            assertEquals(6, response.getPer_page(), "'per_page' mismatch");
            assertEquals(12, response.getTotal(), "'total' mismatch");
            assertEquals(2, response.getTotal_pages(), "'total_pages' mismatch");

            assertNotNull(response.getData(), "Missing 'data'");
            assertEquals(6, response.getData().size(), "'data' size mismatch");

            DataUsers firstUser = response.getData().get(0);
            assertNotNull(firstUser.getId(), "Missing 'id' in first user");
            assertEquals(1, firstUser.getId(), "'id' mismatch in first user");

            assertNotNull(firstUser.getEmail(), "Missing 'email' in first user");
            assertEquals("george.bluth@reqres.in", firstUser.getEmail(), "'email' mismatch in first user");

            assertNotNull(firstUser.getFirst_name(), "Missing 'first_name' in first user");
            assertEquals("George", firstUser.getFirst_name(), "'first_name' mismatch in first user");

            assertNotNull(firstUser.getLast_name(), "Missing 'last_name' in first user");
            assertEquals("Bluth", firstUser.getLast_name(), "'last_name' mismatch in first user");

            assertNotNull(firstUser.getAvatar(), "Missing 'avatar' in first user");
            assertEquals("https://reqres.in/img/faces/1-image.jpg", firstUser.getAvatar(), "'avatar' URL mismatch in first user");
        });

        step("Verify 'support' section", () -> {
            assertNotNull(response.getSupport(), "Missing 'support'");
            assertNotNull(response.getSupport().getUrl(), "Missing 'url' in 'support'");
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl(), "'url' mismatch in 'support'");

            assertNotNull(response.getSupport().getText(), "Missing 'text' in 'support'");
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.", response.getSupport().getText(), "'text' mismatch in 'support'");
        });
    }
}
