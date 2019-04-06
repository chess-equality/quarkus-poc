package org.chessequality.quarkus.poc.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testHelloEndpoint() {

        String uuid = UUID.randomUUID().toString();

        given()
            .pathParam("name", uuid)
            .when().get("/greeting/hello/{name}")
            .then()
                .statusCode(200)
                .body(is("Hello " + uuid));
    }

    @Test
    void testGreetingEndpoint() {

        given()
            .when().get("/greeting")
            .then()
                .statusCode(200)
                .body(is("Hello"));
    }

}