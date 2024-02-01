package GoRestÜbung;

import io.restassured.http.ContentType;
import GoRest.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GoRestUserTestsUbung {

    @BeforeClass
    void SetUp(){

        baseURI="https://gorest.co.in/public/v2/";
    }

   //todo ****************************************************
    public String getRandomName(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase();

    }
    public String getRandomEmail(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }
    //todo********************************************************



    int userId=0;
    Userr userr;
    @Test
    public void createUserObject() {


        userr.setName(getRandomName());
        userr.seteMail(getRandomEmail());
        userr.setGender("male");
        userr.setStatus("active");

        userId=

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
        .contentType(ContentType.JSON)
                .body(userr)
                .log().body()


                .when()
                .post("users")


                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().jsonPath().getInt("id")


                ;
        System.out.println("userrId = "+userId);

    }
    @Test(dependsOnMethods = "createUserObject",priority = 1)
    public void updateUserr(){

       userr.setName("Serkan Baskan");
        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .body(userr)
                .contentType(ContentType.JSON)
                .pathParam("UserId",userId)

                .when()
                .put("users/{UserId}")


                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(200)
                .body("userId",equalTo("Serkan Baskan"))

                ;
    }
    @Test(dependsOnMethods = "updateUserr",priority = 2)
    public void getById(){

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .pathParam("UserId",userId)

                .when()
                .get("users/{userId}")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("id",equalTo(userId))


                ;
    }
    @Test(dependsOnMethods = "")
    public void deleteUser(){

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .log().body()
                .pathParam("UserId",userId)

                .when()
                .delete("users/{userId}")

                .then()
                .log().body()
                .statusCode(204)



        ;
    }
    @Test(dependsOnMethods = "")
    public void deleteUserNegatif(){

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .log().body()
                .pathParam("UserId",userId)

                .when()
                .delete("users/{userId}")

                .then()
                .log().body()
                .statusCode(404)



        ;
    }
    @Test(dependsOnMethods = "")
    public void getUsers(){

        given()
                .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")

                .when()
                .get("users")

                .then()
                .log().body()
                .statusCode(200)
                .extract().response()



        ;
    }

}
