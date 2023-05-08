package GoRestÜbung;

import io.restassured.http.ContentType;
import GoRest.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
public class GoRestUserTestsUbung {

    @BeforeClass
    void SetUp(){

        baseURI="https://gorest.co.in/public/v2/";
    }
    public String getRandomName(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase();

    }
    public String getRandomEmail(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

    @Test
    public void createUserObject() {

        Userr userr = new Userr();
        userr.setName(getRandomName());
        userr.seteMail(getRandomEmail());
        userr.setGender("male");
        userr.setStatus("active");

        int userrId=

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
        .contentType(ContentType.JSON)
                .body(userr)
                .log().body()


                .when()
                .post("users")


                .then()
                .log().body()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .extract().path("id")


                ;
        System.out.println("userrId = "+userrId);

    }

}
