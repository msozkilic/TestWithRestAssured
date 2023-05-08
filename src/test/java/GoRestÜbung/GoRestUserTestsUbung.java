package GoRestÜbung;

import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;
public class GoRestUserTestsUbung {

    @BeforeClass
    void SetUp(){

        baseURI="https://gorest.co.in/public/v2/users";
    }
}
