package week3.day2;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class IncidentE2ETest {
	
	String sys_id;
	
	static String update = """
			{             
              "description": "Update existing record using PUT method",
              "state": "2"
            }
			""";
	
	@DataProvider
	public String[][] testData() {
		return new String[][] {
			{"Data from TestNG Dataprovider", "Create new record using Post method"}
		};
	}
	
	@Test(dataProvider = "testData", priority = 1)
	public void create_a_record(String shortDescription, String description) {
		
		InicidentReqPayload incident = new InicidentReqPayload();
		incident.setShort_description(shortDescription);
		incident.setDescription(description);
		
		sys_id = given()
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
		        .contentType(ContentType.JSON)
		        .extract()
		        .jsonPath()
		        .getString("result.sys_id");
	}
	
	@Test(priority = 2)
	public void retrieve_a_record() {
		given()
		.baseUri("https://dev265761.service-now.com")
        .basePath("/api/now/table")
        .auth().basic("admin", "d@9IvhOh5DR*")
        .pathParam("tableName", "incident")
        .pathParam("sys_id", sys_id)
        .log().all()
        .when()
        .get("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .statusCode(200)
        .statusLine(Matchers.containsString("OK"))
        .contentType(ContentType.JSON)
        .body("result.sys_id", Matchers.equalTo(sys_id))
        .time(Matchers.lessThan(2000L));
	}
	
	@Test(priority = 3)
	public void update_a_record() {
		given()
		.baseUri("https://dev265761.service-now.com")
        .basePath("/api/now/table")
        .auth().basic("admin", "d@9IvhOh5DR*")
        .pathParam("tableName", "incident")
        .pathParam("sys_id", sys_id)
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .body(update)
        .put("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .statusCode(200)
        .statusLine(Matchers.containsString("OK"))
        .contentType(ContentType.JSON)
        .body("result.sys_id", Matchers.equalTo(sys_id))
        .body("result.state", Matchers.equalTo("2"));
	}
	
	@Test(priority = 4)
	public void delete_a_record() {
		given()
		.baseUri("https://dev265761.service-now.com")
        .basePath("/api/now/table")
        .auth().basic("admin", "d@9IvhOh5DR*")
        .pathParam("tableName", "incident")
        .pathParam("sys_id", sys_id)
        .when()
        .delete("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .statusCode(204)
        .statusLine(Matchers.containsString("No Content"));		
	}
	
	@Test(priority = 5)
	public void validateIsDeletionSuccessfull() {
		given()
		.baseUri("https://dev265761.service-now.com")
        .basePath("/api/now/table")
        .auth().basic("admin", "d@9IvhOh5DR*")
        .pathParam("tableName", "incident")
        .pathParam("sys_id", sys_id)
        .log().all()
        .when()
        .get("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .statusCode(404)
        .statusLine(Matchers.containsString("Not Found"))
        .contentType(ContentType.JSON)
        .time(Matchers.lessThan(2000L));
	}

}