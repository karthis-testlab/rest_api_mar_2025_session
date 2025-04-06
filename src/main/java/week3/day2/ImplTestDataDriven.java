package week3.day2;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class ImplTestDataDriven {
	
	@DataProvider
	public String[][] testData() {
		return new String[][] {
			{"Data from TestNG Dataprovider 1", "Create new record using Post method 1"},
			{"Data from TestNG Dataprovider 2", "Create new record using Post method 2"},
			{"Data from TestNG Dataprovider 3", "Create new record using Post method 3"},
			{"Data from TestNG Dataprovider 4", "Create new record using Post method 4"}
		};
	}
	
	@Test(dataProvider = "testData")
	public void createMultipleRecords(String shortDescription, String description) {
		
		InicidentReqPayload incident = new InicidentReqPayload();
		incident.setShort_description(shortDescription);
		incident.setDescription(description);
		
		given()
			.baseUri("https://dev265761.service-now.com")
		    .basePath("/api/now/table")
		    .auth().basic("admin", "d@9IvhOh5DR*")		           
		    .contentType(ContentType.JSON)
		    .pathParam("tableName", "incident")
		    .log().all()
		    .when()
		    .body(incident)
		    .post("/{tableName}")
		    .then()
		    .log().all()
		    .assertThat()
		    .statusCode(201)
		    .statusLine(Matchers.containsString("Created"))
		    .contentType(ContentType.JSON);
		        		
	}

}