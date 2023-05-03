package Campus;

import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CountryTest {
    Cookies cookies;
    @BeforeClass
    public void loginCampus(){

        baseURI = "https://test.mersys.io/";

        //username: turkeyts
        //password: TechnoStudy123

        Map<String,String> credential=new HashMap<>();
        credential.put("username","turkeyts");
        credential.put("password","TechnoStudy123");
        credential.put("rememberMe","true");  //todo burasi kullaniciyi hatirla kismidir

                                                            //todo bununla gönderiyoruz.
        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()

                ;
    }

    String countryID;
    String countryName;
    String countryCode;
    @Test
    public void createCountry(){
        countryName=getRandomName();
        countryCode=getRandomCode();

        Country country=new Country();
        country.setName(countryName);//todo generateCountryName
        country.setCode(countryCode);//todo generateCountryCode

        countryID=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("school-service/api/countries")

                .then()

                .statusCode(201)
                .extract().jsonPath().getString("id")

                ;

    }
    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegatif(){


        Country country=new Country();
        country.setName(countryName);//todo generateCountryName
        country.setCode(countryCode);//todo generateCountryCode


                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)

                        .when()
                        .post("school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", equalTo("The Country with Name \""+countryName+"\" already exists."))

        ;
        //todo getRandomName i almadigim icin global olan degiskenin son hali vardi
        //todo burdada countryName deyince createCountryde olusan geldi dolayisiyla negatif oldu

    }
    @Test
    public void updateCountry(){
        countryName=getRandomName();


        Country country=new Country();
        country.setId(countryID);
        country.setName(countryName);//todo generateCountryName
        country.setCode(countryCode);

                         given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)

                        .when()
                        .put("school-service/api/countries")

                        .then()

                        .statusCode(200)
                        .body("name",equalTo(countryName))

        ;

    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountryById(){

        given()
                        .cookies(cookies)
                        .pathParam("countryID",countryID)

                        .when()
                        .delete("school-service/api/countries/{countryID}")

                        .then()
                        .log().body()
                        .statusCode(200)
        ;
    }
    @Test(dependsOnMethods = "deleteCountryById")
    public void deleteCountryByIdNegative(){

        given()
                .cookies(cookies)
                .pathParam("countryID",countryID)

                .when()
                .delete("school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(400)


        ;

    }
    @Test(dependsOnMethods = "deleteCountryById")
    public void updateCountryNegative(){ //todo bunu delete den sonra calistiriyoruz ki olmayan bir seyi yenilemeye calissin

        countryName=getRandomName();
        Country country=new Country();
        country.setId(countryID);
        country.setName(countryName);//todo generateCountryName
        country.setCode(countryCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))

        ;

    }
    public String getRandomName(){  //todo sürekli bir isim alacagimiz icin bunu metoda cevirdik.

        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode(){

        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }


}
