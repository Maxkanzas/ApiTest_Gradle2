package api;
import models.books.AddBookList;
import models.books.DataUsers;
import static io.restassured.RestAssured.given;
import static specs.demoqabookstore.BaseRequestSpec.bookRequestSpec;
import static specs.demoqabookstore.BaseResponseSpec.baseResponseSpec;
public class BookApiSteps {
    public void deleteAllBooks(DataUsers dataUsers) {
        given(bookRequestSpec)
                .header("Authorization", "Bearer " + dataUsers.getToken())
                .queryParams("UserId", dataUsers.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(baseResponseSpec)
                .extract().response();
    }
    public void deleteBook(DataUsers dataUsers, String isbn) {
        String deleteBookData = String.format("{\"userId\":\"%s\",\"isbn\":\"%s\"}", dataUsers.getUserId(), isbn);
        given(bookRequestSpec)
                .header("Authorization", "Bearer " + dataUsers.getToken())
                .queryParams("UserId", dataUsers.getUserId())
                .body(deleteBookData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(baseResponseSpec)
                .extract().response();
    }
    public void addBook(DataUsers dataUsers, AddBookList booksList) {
        given(bookRequestSpec)
                .header("Authorization", "Bearer " + dataUsers.getToken())
                .body(booksList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(baseResponseSpec)
                .extract().response();
    }
}
