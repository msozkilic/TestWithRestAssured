package Campus;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
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

        cookies=
        given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login") //todo url nin devami

                .then()
              //  .log().all()  //todo
                .statusCode(200)
                .extract().response().getDetailedCookies();//todo burada su var.sayfanin tooken i cookies altinda gidiyordu
                                                            //todo bununla gönderiyoruz.

                ;
    }
    @Test
    public void createCountry(){
        given()
                .cookies(cookies)
                .log().all()

                .when()


                .then()

                ;

    }

}
