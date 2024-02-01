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
    public void test() {


                  given()//hazirlik islemleri,send,body,parametreler
                 .when()//link ve metod verilecek
                   .then()// assertion ve verileri ele alma ,extract
                  ;}

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
    public void contentTypeTest(){


        given()

                .when()
                .get("")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }


    @Test
    public void checkStateInResponseBody(){

        given()

                .when()
                .get("")

                .then()
                .contentType(ContentType.JSON)
                .body("country",equalTo("unitedStates"))
                .statusCode(200);
    }


    @Test
    public void bodyJsonPathTest2(){

        given()

                .when()
                .get("")

                .then()
                .log().body()
                .body("places[0].state",equalTo("California"))
                .statusCode(200);
    }

    @Test
    public void bodyJsonPathTest(){
        given()


                .when()
                .get("")

                .then()
                .contentType(ContentType.JSON)
                .body("places.state",equalTo("California"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonTest(){
        given()

                .when()
                .get("")

                .then()
                .log().body()
                .body("places.'places name'",hasItem("CaputcuKöyü"))
                .statusCode(200);
    }

    @Test
    public void bodyJsonPathTest4(){

        given()

                .when()
                .get("")

                .then()
                .log().body()
                .body("places.'place name'",hasSize(1))
                .statusCode(200);
    }

    @Test
    public void pathParamTest(){

        given()
                .pathParam("Country","us")
                .pathParam("ZipCode","90210")
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipCode}")


                .then()
                .log().body()
                .statusCode(200)

                ;
    }

    @Test
    public void pathParamTest2() {

        for (int i = 90210; i < 90213; i++) {


            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipCode", "i")
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipCode}")


                    .then()
                    .log().body()
                    .body("places", hasSize(1))
                    .statusCode(200);
        }
    }

    @Test
    public void queryParamTest(){

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
    public  void queryParamTest2(){

        given()
                .param("page",1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .statusCode(200);
    }

    @Test
    public void queryParamTest3() {

        for (int pageNo = 1; pageNo <= 10; pageNo++) {


            given()
                    .param("page",pageNo )
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v2/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page",equalTo(pageNo))
                    .statusCode(200);
        }
    }

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void SetUp(){

        baseURI="https://gorest.co.in/public/v1";
        requestSpecification=new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification=new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecipication(){

        given()
                .param("page",1)
                .spec(requestSpecification)

                .when()
                .get("/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))
                .spec(responseSpecification);

    }















































}
