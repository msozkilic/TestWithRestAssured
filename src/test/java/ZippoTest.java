import io.restassured.http.ContentType;
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

}
