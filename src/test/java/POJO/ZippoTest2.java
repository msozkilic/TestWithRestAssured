package POJO;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
    @Test
    public void bodyJsonPathTest4(){
        given()


                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'",hasItem("Çaputçu Köyü"))
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
    public void pathParamTest(){
        given()
                .pathParam("Country","us")
                .pathParam("ZipKod","90210")
                .log().uri()


                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                .then()
                .log().body()
                .statusCode(200)

        ;
    }
    @Test
    public void pathParamTest2() {
        //todo 90210 dan 90250 ye kadar test sonuclarinda places size nin hepsinde 1 geldigini test ediniz

        for (int i = 90210; i < 90250; i++) {

            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipKod", i)
                    .log().uri()


                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                    .then()
                    .log().body()
                    .body("places", hasSize(1))
                    .statusCode(200)

            ;
        }
    }
    @Test
    public void queryParamTest(){
        //  https://gorest.co.in/public/v2/users?page=1
        given()
                .param("page",1)
                .log().uri()


                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .statusCode(200)

        ;
    }
    @Test
    public void queryParamTest2(){
        //  https://gorest.co.in/public/v2/users?page=1
        given()
                .param("page",1)
                .log().uri()


                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200)

        ;
    }





}
