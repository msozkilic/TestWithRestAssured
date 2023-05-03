package GoRest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

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
    User newUser;

    @Test   // todo 1-postmandeki create yani post kismini yaptik.Yani bir isim,mail,cinsiyet ve status tanimladik.
    public void createUserObject(){

        new User();
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
    @Test(dependsOnMethods ="createUserObject",priority = 1) //todo 2-postmandeki Update yani put kismini yaptik
    public void updateUserObject(){

        //Map<String,String>updateUser=new HashMap<>(); //todo önce Map ile yapmistik.sonra user classtan alarak altta yaptik
        //updateUser.put("name","serkan kilic");        //

        newUser.setName("serkan kilic");

        given()
                        .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                        .contentType(ContentType.JSON)
                        .body(newUser)         //todo bodynin icine map ile yapmissak updateUser,class ile yapmissak newUser yaziyoruz
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
    @Test(dependsOnMethods ="createUserObject" ,priority = 2) //todo 3-postmandeki idyi al kontrol et yani get kismini yaptik
    public void getUserByID(){

        given()
                .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID) //todo burayi alttaki get icinde String olarak kullanmak icin yapiyoruz.Yani bize gelen userID ,userID olarak kullanilsin dedik.+++

                .when()
                .get("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))  //todo postmanin bize verdigi id ile url nin sonuna gelen id ayni mi
                                                //todo ikisi de ayni olmali ki dogru olsun

        ;


    }
    @Test(dependsOnMethods ="createUserObject" ,priority = 3)   //todo 4-postmandeki idyi al ve sil dedik
    public void deleteUserByID(){

        given()
                .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(204)

        ;


    }
    @Test(dependsOnMethods ="deleteUserByID" ,priority = 4)   //todo 4-Silinen usersi sil dedik tekrar.404 hatasi verirse dogrudur.
    public void deleteUserByIdnegatif(){

        given()
                .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(404)
        ;
    }
    @Test //todo 4-postmandeki tüm listeyi alacagiz
    public void getUsers() {

        Response response =
                given()
                        .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")

                        .when()
                        .get("users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        //todo 3.usersin id sini aliniz(path ve jsonpath ile ayri ayri yapiniz
        //todo sorulari cözerken webden hazir gösteren sayfadan yardim aldik.jsonpathfinder

        int idUser3 = response.path("[2].id");
        int idUser3JsonPath = response.jsonPath().getInt("[2].id");
        System.out.println("idUser3path "+ idUser3JsonPath);
        System.out.println("idUser3JsonPath "+ idUser3JsonPath);


        //todo tüm gelen veriyi bir nesneye atinih
        User[] userPath=response.as(User[].class);
        System.out.println("Arrays.toString(usersPath) ="+ Arrays.toString(userPath));

        List<User> usersJsonPath=response.jsonPath().getList("", User.class);
        System.out.println("usersJsonPath " +usersJsonPath);

    }

    //todo SORU getuserbyid testinde dönen useri bir nesneye atiniz.User classindan türetilen User nesnesine attik.
    @Test
    public void deleteUserByIDExtract(){
        User user=
        given()
                .header("Authorization","Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",3414)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
               // .extract().as(User.class) //todo burda class olarak hepsini aldik,user nesnesinin icine attik.normalde extract.path("id") deyince sadece id yi aliyordu.
                                            //todo extract.path([1].id) ile de dizi halinde 1 indexli id yi aldik
                                            //todo classin hepsini almak icin ise extract.as ile tüm klasi aliyor,nesneye atiyoruz.ondan sonra direkt ulasabiliyoruz.user.id,user.name mesela
                .extract().jsonPath().getObject("",User.class) //todo extract as in aynisidir.
        ;


    }
    @Test
    public void getUsersV1() {

        Response response =
                given()
                        .header("Authorization", "Bearer c2668e9cfb33f884ca5b66f5cc8e8acba4e2b151e47c88a362113bef8d6edbd9")

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        List<User> dataUsers=response.jsonPath().getList("data",User.class);//todo User adli classin icinden sadece data kismini aldik.

    }
    class User{        //todo

        private  int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", email='" + email + '\'' +
                    ", status='" + status + '\'' +
                    '}';
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
