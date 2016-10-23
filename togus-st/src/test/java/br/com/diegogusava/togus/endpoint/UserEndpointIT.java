package br.com.diegogusava.togus.endpoint;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;


public class UserEndpointIT {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = System.getProperty("restassured.applicationpath", "/togus/rest/v1");
    }

    @Test
    public void findAllResultOk() {
        get("/users").then().assertThat().statusCode(200);
    }

    @Test
    public void persistUserOk() {
        final Response post = given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Diego Armando Gusava\",\"email\": \"diegogusava@gmail.com\"}")
                .post("/users");

        Assert.assertEquals(201, post.statusCode());

        final int id = from(post.asString()).getInt("id");

        given()
                .pathParam("id", id)
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Diego Armando Gusava"));
    }

    @Test
    public void deleteUserOk() {
        final Response post = given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Diego Armando Gusava\",\"email\": \"diegogusava@gmail.com\"}")
                .post("/users");

        Assert.assertEquals(201, post.statusCode());

        final int id = from(post.asString()).getInt("id");

        given()
                .pathParam("id", id)
                .delete("/users/{id}")
                .then()
                .assertThat()
                .statusCode(204);

        given()
                .pathParam("id", id)
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void updateOk() {
        final Response post = given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Diego Armando Gusava\",\"email\": \"diegogusava@gmail.com\"}")
                .post("/users");

        Assert.assertEquals(201, post.statusCode());
        final String location = post.getHeader("location");
        Assert.assertThat(location, not(isEmptyOrNullString()));

        final int id = from(post.asString()).getInt("id");


        given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Diego Gusava\",\"email\": \"diego@gmail.com\"}")
                .pathParam("id", id)
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(204);

        given()
                .pathParam("id", id)
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Diego Gusava"));
    }
}
