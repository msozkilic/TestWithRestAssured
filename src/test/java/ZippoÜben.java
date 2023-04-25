import io.restassured.http.ContentType;
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



}
