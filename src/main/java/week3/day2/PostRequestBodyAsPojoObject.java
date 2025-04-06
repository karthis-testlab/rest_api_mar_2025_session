package week3.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestBodyAsPojoObject {
	
	public static void main(String[] args) {	
		
		InicidentReqPayload incident = new InicidentReqPayload();
		incident.setDescription("test description");
		incident.setShort_description("test short description");
		
		RestAssured.given()
		           .baseUri("https://dev265761.service-now.com")
		           .basePath("/api/now/table")
		           .auth().basic("admin", "d@9IvhOh5DR*")		           
		           .contentType(ContentType.JSON)
		           .log().all()
		           .when()
		           .body(incident)
		           .post("/incident")
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(201);
	}
	
	
	static InicidentReqPayload payload(String shortDescription, String description) {
		InicidentReqPayload incident = new InicidentReqPayload();
		incident.setShort_description(shortDescription);
		incident.setDescription(description);
		return incident;
	}

}