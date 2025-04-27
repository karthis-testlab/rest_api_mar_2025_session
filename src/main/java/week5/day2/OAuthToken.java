package week5.day2;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class OAuthToken {
	
	private String accessToken = "{\"message\": \"Welcome To Wiremock!\"}";
	
	@Test
	public void createOAuthToken() {
		accessToken = given()
		   .baseUri("https://dev265761.service-now.com")
		   .contentType(ContentType.URLENC)
		   .formParam("grant_type", "password")
		   .formParam("client_id", "<client_id>")
		   .formParam("client_secret", "<client_secret>)")
		   .formParam("username", "admin")
		   .formParam("password", "<incident_password>")
		   .log().all()
		   .when()		   
		   .post("/oauth_token.do")
		   .then()
		   .log().all()
		   .assertThat()
		   .statusCode(200)
		   .extract()
		   .jsonPath()
		   .getString("access_token");
		   
	}
	
	@Test
	public void validateIncidentGetRecords() {
		given()
		.baseUri("https://dev265761.service-now.com")
		.basePath("/api/now/table")
		.header("Authorization", "Bearer "+accessToken)
		.log().all()
		.when()
		.get("/incident")
		.then()
		.log().all()
		.assertThat()
		.statusCode(200);
		
	}

}