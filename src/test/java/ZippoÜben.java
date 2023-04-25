import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ZippoÜben {

    @Test
    public void test(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200)


                ;
    }


}
