package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GoRestUserTests {

    @BeforeClass
    void SetUp(){
        baseURI="https://gorest.co.in/public/v2/";
    }
    @Test
    public void createUser(){
        int userID=
        given()
                .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"Male\", \"email\":\"\"+getRandomEmail()+\"\", \"status\":\"Active\"}")

                .when()
                .post("users")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id")
                ;
        System.out.println("userID ="+ userID);

    }
    public String getRandomName(){
    return RandomStringUtils.randomAlphabetic(8);
    }
    public String getRandomEmail(){
    return RandomStringUtils.randomAlphabetic(8)+"@gmail.com";
    }
    @Test
    public void createUserMap(){
        int userID=
                given()
                        .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"Male\", \"email\":\"\"+getRandomEmail()+\"\", \"status\":\"Active\"}")

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;
        System.out.println("userID ="+ userID);

    }

}
