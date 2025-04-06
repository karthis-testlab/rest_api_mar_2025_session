package week3.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestBodyAsString {
	
	static String req_body = """
			{
              "short_description": "RESTAPISessionMAR2025",
              "description": "Create new record using Post method"
            }
			""";

	public static void main(String[] args) {
		RestAssured.given()
		           .baseUri("https://dev265761.service-now.com")
		           .basePath("/api/now/table")
		           .auth().basic("admin", "d@9IvhOh5DR*")		           
		           .contentType(ContentType.JSON)
		           .log().all()
		           .when()
		           .body(req_body)
		           .post("/incident")
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(201);
	}

}