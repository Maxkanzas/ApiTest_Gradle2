import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class StatusTest {
    @Test
    public void checkStatus() {
        given()
                .log().all()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20))
                .log().all();
    }
}
