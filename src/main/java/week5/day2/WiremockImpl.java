package week5.day2;

import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

public class WiremockImpl {
	
	WireMockServer mockServer = new WireMockServer(8181);
	
	@Test
	public void wiremockTest() {
		
		mockServer.start();
		
		mockServer.stubFor(
				  WireMock.post("/welcome")
				  .willReturn(WireMock.aResponse()
						  .withStatus(200)
						  .withBody("Welcome to Wiremock Java Client")
						  )
				);
		
		RestAssured.given()
		           .baseUri("http://localhost:8181")
		           .log().all()
		           .when()
		           .get("/welcome")
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(200);
		
		mockServer.stop();
		
	}

}