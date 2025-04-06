package week3.day2;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestBodyAsFile {
	
	public static void main(String[] args) {
		RestAssured.given()
		           .baseUri("https://dev265761.service-now.com")
		           .basePath("/api/now/table")
		           .auth().basic("admin", "d@9IvhOh5DR*")		           
		           .contentType(ContentType.JSON)
		           .log().all()
		           .when()
		           .body(new File("src/main/resources/req_payloads/Create_Incident.json"))
		           .post("/incident")
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(201);
	}

}