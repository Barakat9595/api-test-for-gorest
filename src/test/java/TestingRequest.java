import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class TestingRequest extends Authentication {

    @Test
    public void testFirstRequest() {
        Response response =
                given()
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .delete("https://gorest.co.in/public/v2/users/7374234");




        // Extract & Print Key Response Details (Like Postman)
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body().asString());

    }
}
