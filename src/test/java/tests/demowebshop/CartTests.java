package tests.demowebshop;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CartTests extends TestBase {
    @Test
    public void addToCartAsAuthorizeitedTest() {
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
        String data = "product_attribute_72_5_18=65" +
                "&product_attribute_72_6_19=55" +
                "&product_attribute_72_3_20=5" +
                "8&addtocart_72.EnteredQuantity=1";

        given()
                .cookie(authCookieKey, authCookieValue)
                .body(data)
                .when()
                .post("/cart")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.body.'**'.find { it.@class == 'cart-qty' }", is("(21)"));

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieKey, authCookieValue)
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your \u003ca href=\"/cart\"\u003eshopping cart\u003c/a\u003e"))
                .body("updatetopcartsectionhtml", is("(22)"));
    }
    @Test
    public void addToCartAsAnonimTest() {
        String data = "product_attribute_72_5_18=65" +
                "&product_attribute_72_6_19=55" +
                "&product_attribute_72_3_20=5" +
                "8&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your \u003ca href=\"/cart\"\u003eshopping cart\u003c/a\u003e"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }
}