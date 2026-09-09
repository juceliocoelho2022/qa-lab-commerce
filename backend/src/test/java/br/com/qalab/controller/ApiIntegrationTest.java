package br.com.qalab.controller;

import br.com.qalab.QaLabApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = QaLabApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("qalab").withUsername("qalab").withPassword("qalab");

    @DynamicPropertySource
    static void database(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @LocalServerPort int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldLoginAndListProducts() {
        String token = given().contentType(ContentType.JSON)
                .body("{\"email\":\"qa.user@qalab.dev\",\"password\":\"user123\"}")
                .when().post("/api/auth/login")
                .then().statusCode(200).body("tokenType", equalTo("Bearer"))
                .extract().path("accessToken");

        given().header("Authorization", "Bearer " + token)
                .when().get("/api/products")
                .then().statusCode(200).body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    void customerShouldNotCreateProduct() {
        String token = given().contentType(ContentType.JSON)
                .body("{\"email\":\"qa.user@qalab.dev\",\"password\":\"user123\"}")
                .post("/api/auth/login").then().extract().path("accessToken");

        given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)
                .body("{\"name\":\"Monitor\",\"sku\":\"MON-999\",\"price\":899.90,\"stock\":3}")
                .when().post("/api/products")
                .then().statusCode(403);
    }
}
