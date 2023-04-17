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

public class ZippoTest {

    @Test
    public void test(){

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when() // todo lin ve metod verilecek

                .then();// todo assertion ve verileri ele alma ,extract
    }

    @Test
    public void statusCodeTest(){

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then() // todo dogrulama yeri ,statüs
                .log().body() //todo log.All bütün responsu sonucu listeyi verir
                .statusCode(200)

        ;
    }
    @Test
    public void contentTypeTest(){

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then() // todo dogrulama yeri ,statüs
                .log().body() //todo log.All bütün responsu sonucu listeyi verir
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;
    }
    @Test
    public void checkStateInResponseBody(){

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then() // todo dogrulama yeri ,statüs
                .log().body() //todo log.All bütün responsu sonucu listeyi verir
                .body("country",equalTo("United States"))//todo body.country==United States ?
                .statusCode(200)

        ;
    }
    // todo body.places[0].'place name' -> body("body.places[0].'place name'"   seklinde yazilir hamcrest i

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


}
