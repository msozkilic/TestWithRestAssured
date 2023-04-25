import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoÜben {

    @Test
    public void test(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200)


                ;
    }
    @Test
    public void contentType(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }
    @Test
    public void checkStateInResponseBody(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("country",equalTo("United State"))
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }
    @Test
    public void bodyJsonPathTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                .log().body()
                .body("places.'place name'",hasItem("Camuzcu Köyü"))
                .statusCode(200)

        ;
    }
    @Test
    public void bodyArrayHasSize(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .statusCode(200)

                ;
    }
    @Test
    public void combiningTest()
    {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .body("places.state",hasItem("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .statusCode(200)

                ;
    }
    @Test
    public void pathParamTest(){
        given()
                .pathParam("Country","us")
                .pathParam("ZipKod",90210)
                .log().uri()


                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")


                .then()
                .log().body()
        ;
    }
    @Test
    public void pathParam2() {

        for (int i = 90210; i < 90213; i++) {

            given()

                    .when()
                    .pathParam("Country", "us")
                    .pathParam("ZipKod", i)

                    .get("http://api.zippopotam.us/Country/Zipkod")


                    .then()
                    .log().body()
                    .body("places",hasSize(1))
                    .statusCode(200)

            ;
        }
    }

    @Test
    public void queryParamTest(){
        given()
                .param("page",1)

                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200)


                ;
    }
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    void setUp(){
        baseURI="https://gorest.co.in/public/v1";

        requestSpecification=new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification =new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }
    @Test
    public void requestResponseSpecification(){
        given()
                .param("page",1)
                .spec(requestSpecification)

                .when()
                .get("/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .spec(responseSpecification)

                ;
    }
    @Test
    public void extractingJsonPath(){
       String placeName=

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                .statusCode(200)
                .extract().path("places[0].'place name'" )

                ;
       System.out.println("place name ="+placeName);

    }



}
