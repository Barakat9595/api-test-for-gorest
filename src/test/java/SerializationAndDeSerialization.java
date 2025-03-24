import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SerializationAndDeSerialization extends Authentication{

    String userId;

    //Serialization
    @Test(dataProvider = "csvData", dataProviderClass = DataProviders.class)
        void createUser(String name, String gender, String email, String status) throws JsonProcessingException {
            PojoPostReq data = new PojoPostReq();
            data.setName(name);
            data.setGender(gender);
            data.setEmail(email);
            data.setStatus(status);
            //serialization step --> converting object to json
            ObjectMapper objectMap = new ObjectMapper();
            String jsonBody = objectMap.writerWithDefaultPrettyPrinter().writeValueAsString(data);

            //Sending the request
            //saving response to deal with it's elements
            Response response = given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .body(jsonBody)

                    .when()
                    .post("https://gorest.co.in/public/v2/users");


            response.then().statusCode(201).log().all();




    }

}
