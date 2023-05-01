package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GoRestUserTests { //todo gorest sayfasinin API testini yapiyoruz
    @BeforeClass        //todo sayfaya her classta baglanacagimiz icin bunu before class yaptik.
    void SetUp(){
        baseURI="https://gorest.co.in/public/v2/";
    }
    public String getRandomName(){  //todo sürekli bir isim alacagimiz icin bunu metoda cevirdik.
    return RandomStringUtils.randomAlphabetic(8);
    }
    public String getRandomEmail(){
    return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

    int userID=0;           //todo userID yi her classta kullanavagimiz icin bunu classin disinda tanimladik.Global oldu.
                            //todo bu userID url nin sonundaki kisim demek

    @Test
    public void createUserObject(){ // todo 1-postmandeki create yani post kismini yaptik.Yani bir isim,mail,cinsiyet ve status tanimladik.

        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setEmail(getRandomEmail());
        newUser.setGender("male");
        newUser.setStatus("active");

         userID=
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
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id") //todo otomatik inte cevirdi
                ;
        System.out.println("userID ="+ userID);

    }
    @Test(dependsOnMethods ="createUserObject" ) //todo 2-postmandeki Update yani put kismini yaptik
    public void updateUserObject(){

        Map<String,String>updateUser=new HashMap<>();
        updateUser.put("name","serkan kilic");

        given()
                        .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .log().body()
                        .pathParam("userID",userID)

                        .when()
                        .put("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name",equalTo("serkan kilic"))

                ;


    }
    class User{        //todo
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
    @Test(enabled = false)  //todo bu ve alttaki classlari hoca giris olarak ögretmek icin yazdi.
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
    @Test(enabled = false)
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

}
