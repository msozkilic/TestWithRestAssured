package Campus;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class CountryTest {

    @BeforeClass
    public void loginCampus(){

        baseURI = "https://test.mersys.io/";

        //username: turkeyts
        //password: TechnoStudy123

        Map<String,String> credential=new HashMap<>();
        credential.put("username","turkeyts");
        credential.put("password","TechnoStudy123");
        credential.put("rememberMe","true");  //todo burasi kullaniciyi hatirla kismidir

        given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login") //todo url nin devami


                .then()
                .log().all()
                .statusCode(200)

                ;



    }

}
