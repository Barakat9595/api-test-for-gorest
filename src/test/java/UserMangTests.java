import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserMangTests extends Authentication {
    int userId;
    @Test (priority = 1)
    public void createNewUser()
    {
        HashMap requestBody =new HashMap<>();
        requestBody.put("name", "MrTest");
        requestBody.put("gender", "male");
        requestBody.put("email", "testUser14@15ce.com");
        requestBody.put("status", "active");


        //saving response to deal with it's elements
               Response response = given()
                       .header("Authorization", "Bearer " + token)
                       .contentType(ContentType.JSON)
                       .body(requestBody)

                .when()
                        .post("https://gorest.co.in/public/v2/users");


               response.then().statusCode(201).assertThat();


        //parsing id from response
           userId = response.jsonPath().getInt("id");

        //printing response and status code
        System.out.println("statusCode" + response.statusCode());
        System.out.println("response body" + response.getBody()); //getBody >> show body



    }


    @Test(priority = 2, dependsOnMethods = {"createNewUser"})
    public void showResults()
    {
           Response response =
                   given()
                        .header("Authorization", "Bearer " + token)  //the auth to show my data not the default data
                    .when()
                        .get("https://gorest.co.in/public/v2/users/");

           String id = response.jsonPath().get("[0].id").toString();//json path finder
            Assert.assertEquals(Integer.parseInt(id),userId);




    }

    @Test (priority = 2, dependsOnMethods = {"createNewUser"})
    public void verifyDataIsCorrect()
    {
            given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get("https://gorest.co.in/public/v2/users/"+userId)
                    .then()
                    .assertThat()
                    .body("id",equalTo(userId))
                    .and().body("name",equalTo("MrTest"))
                    //keySet extracts all keys from the item it follows, which is [0] here if it was array but if the response is {} then directly access it
                    .and().body("keySet()", hasItems("id", "name", "email", "gender", "status"))

                    .log().all();
    }

    @Test (priority = 3, dependsOnMethods = {"createNewUser"})
    public void deleteUser()
    {
        given()
                .header("Authorization", "Bearer " + token)

                .when()
                .delete("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .statusCode(204)
                .log().all();
    }

    @Test (priority = 4, dependsOnMethods = {"deleteUser"})
    public void verifyUserIsDeleted()
    {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://gorest.co.in/public/v2/users/"+userId)
                .then()
                .body("message",equalTo("Resource not found"))
                .statusCode(404)
                .log().all();
    }




}