import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Tasks {
/** Task 1
 *  create a request to https://jsonplaceholder.typicode.com/todos/2
 *  expect status 200
 * Converting Into POJO
 */

@Test
    public void Task1(){

    given()

            .when()
            .get("https://jsonplaceholder.typicode.com/todos/2")

            .then()
            .log().body()
            .statusCode(200)


            ;
}
}
