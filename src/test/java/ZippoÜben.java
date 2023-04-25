import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

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



}
