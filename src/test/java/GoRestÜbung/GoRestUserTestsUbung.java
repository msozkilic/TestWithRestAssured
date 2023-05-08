package GoRestÜbung;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;
public class GoRestUserTestsUbung {

    @BeforeClass
    void SetUp(){

        baseURI="https://gorest.co.in/public/v2/users";
    }
    public String getRandomName(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase();

    }
    public String getRandomEmail(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

}
