package week3.day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestAssuredResponseAsXml {

	public static void main(String[] args) {
		RestAssured.given()
              .baseUri("https://dev265761.service-now.com")
              .basePath("/api/now/table")
              .auth().basic("admin", "d@9IvhOh5DR*")
              .accept(ContentType.XML)
              .log().all() // Request Log		          
              .when()
              .get("/incident")
              .then()
              .log().all() // Response Log
              .assertThat()
              .statusCode(200)
              .contentType(ContentType.XML);
	}

}