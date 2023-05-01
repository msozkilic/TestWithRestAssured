package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }
    @Test
    public void createUserMap(){

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email",getRandomEmail());
        newUser.put("status","active");

        int userID=
                given()
                        .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

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
    @Test
    public void createObject(){

        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setEmail(getRandomEmail());
        newUser.setGender("male");
        newUser.setStatus("active");

        int userID=
                given()
                        .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

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
    class User{
        private String name;
        private String gender;

        private  String email;
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
