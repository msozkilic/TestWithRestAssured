package POJO;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ZippoTest2 {

   @Test
    public void test(){

       given()
               .when()
               .then();

    }
    @Test
    public void statusCode(){

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
               .get("")

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
               .contentType(ContentType.JSON)
               .log().body()
               .body("country",equalTo("unitedStates"))
               .statusCode(200)

               ;
    }
    @Test
    public void bodyJsonPathTest2(){
       given()

               .when()
               .get("http://api.zippopotam.us/us/90210")

               .then()
               .log().body()
               .body("places[0].state",equalTo("California"))
               .statusCode(200)

       ;
    }

    @Test
    public void bodyJsonPathTest(){
       given()

               .when()
               .get("http://api.zippopotam.us/us/90210")

               .then()
               .contentType(ContentType.JSON)
               .body("places.state",equalTo("California"))
               .statusCode(200)

               ;
    }


}
