package specs.reqresin;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class ResponseSpec {
    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(anyOf(is(200), is(201)))
            .log(STATUS)
            .log(BODY)
            .build();
    public static ResponseSpecification missingPasswordResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();
}
