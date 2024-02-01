import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when() // todo lin ve metod verilecek

                .then();// todo assertion ve verileri ele alma ,extract
    }

    @Test
    public void statusCodeTest() {

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then() // todo dogrulama yeri ,statüs
                .log().body() //todo log.All bütün responsu sonucu listeyi verir
                .statusCode(200)

        ;
    }

    @Test
    public void contentTypeTest() {

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
    public void checkStateInResponseBody() {

        given()//todo hazirlik islemleri token ,send , body , parametreler

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then() // todo dogrulama yeri ,statüs
                .log().body() //todo log.All bütün responsu sonucu listeyi verir
                .body("country", equalTo("United States"))//todo body.country==United States ?
                .statusCode(200)

        ;
    }
    // todo body.places[0].'place name' -> body("body.places[0].'place name'"   seklinde yazilir hamcrest i

    @Test
    public void bodyJsonPathTest2() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)

        ;
    }

    @Test
    public void bodyJsonPathTest3() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places.state", equalTo("California"))
                .statusCode(200)

        ;
    }

    @Test
    public void bodyJsonPathTest4() {

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'", hasItem("Çaputçu Köyü"))
                .statusCode(200)

        ;
    }

    @Test
    public void bodyArrayHasSize() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1)) //todo verilen path deki listin size kontrolu
                .statusCode(200)

        ;
    }

    @Test
    public void pathParamTest() {

        given()
                .pathParam("Country", "us")
                .pathParam("ZipKod", "90210")
                .log().uri() //todo url yi göster

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

        for (int i = 90210; i < 90213; i++) {
            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipKod", i)
                    .log().uri() //todo url yi göster

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
    public void queryParamTest() {
        //  https://gorest.co.in/public/v2/users?page=1

        given()
                .param("page", 1)
                .log().uri() //todo url yi göster

                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()

                .statusCode(200)

        ;
    }

    @Test
    public void queryParamTest2() {
        //  https://gorest.co.in/public/v1/users?page=1

        given()
                .param("page", 1)
               .log().uri() //todo url yi göster

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .statusCode(200)

        ;


    }

    @Test
    public void queryParamTest3() {
        //  https://gorest.co.in/public/v1/users?page=1
        //todo 1 den 10 a kadar page leri kontrol ediniz yani url de page 1 di,simdi 10 a kadar page i kontrol et.

        for (int pageNo = 1; pageNo <= 10; pageNo++) {


            given()
                    .param("page", pageNo)
                    .log().uri() //todo url yi göster

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(pageNo))
                    .statusCode(200)
            ;
        }
    }

    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void SetUp() {

        baseURI = "https://gorest.co.in/public/v1";//bunu yazinca testte sadece sonunu yaziyorsun
        requestSpecs = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecs = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecificiation() {
        //  https://gorest.co.in/public/v1/users?page=1

        given()
                .param("page", 1)
                .spec(requestSpecs)

                .when()
                .get("/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .spec(responseSpecs)

        ;
    }
    @Test
    public void extractingJsonPath(){

        int limit=
                given()

                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit")
                ;
        System.out.println("limit  "+ limit);
        Assert.assertEquals(limit,10,"test sonucu");


    }
    @Test // todo dizideki idyi aldik
    public void extractingJsonPath2(){

        int id=
                given()

                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data[2].id")
                ;
        System.out.println("id  "+ id);



    }
    @Test // todo dizideki bütün idleri aldik
    public void extractingJsonIntList(){

        List<Integer> idler=
                given()

                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id")
                ;
        System.out.println("id  "+ idler);
        Assert.assertTrue(idler.contains(3045));



    }

    @Test        // todo dizideki bütün nameleri aldik
    public void extractingJsonResponseAll(){

        Response response=
        given()

                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response()
                ;
        List<String> isimler=response.path("data.name");
        List<Integer> idler=response.path("data.id");
        int limit=response.path("meta.pagination.limit");

        System.out.println("limit "+ limit);
        System.out.println("idler "+ idler);
        System.out.println("isimler "+isimler);




    }
    @Test
    public void extractingJsonPojo() {

        Location yer=

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().as(Location.class)

        ;
        System.out.println("yer "+ yer);
        System.out.println("yer.getCountry() ="+yer.getCountry());
        System.out.println("yer.getPlace().get(0).getPlacename()= "+
                yer.getPlaces().get(0).getPlaceName());
    }
    /** Task 1
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */

    @Test
    public  void task2(){
        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)


        ;
    }








}



