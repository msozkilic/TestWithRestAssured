package POJO;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

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


}
