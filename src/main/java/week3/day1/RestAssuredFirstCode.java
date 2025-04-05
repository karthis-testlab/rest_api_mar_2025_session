package week3.day1;

import io.restassured.RestAssured;

public class RestAssuredFirstCode {

	public static void main(String[] args) {
		
		RestAssured.given()
		           .baseUri("https://dev265761.service-now.com")
		           .basePath("/api/now/table")
		           .auth().basic("admin", "d@9IvhOh5DR*")
		           .log().all() // Request Log		          
		           .when()
		           .get("/incident")
		           .then()
		           .log().all() // Response Log
		           .assertThat()
		           .statusCode(200);
		           

	}

}