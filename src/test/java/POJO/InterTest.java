package POJO;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class InterTest {
    //todo bu classi APi key ile nasil baglanilir onu göstermek icin yaptik

    @Test
    void testApiKey(){

        given()
                .header("x-api.key","GwMco9Tpstd5vbzlzW9I7hr6E1D7w2zEIrhOra")

                .when()
                .get("https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user")

                .then()
                .log().body()
                .statusCode(200)

                ;
    }
}
