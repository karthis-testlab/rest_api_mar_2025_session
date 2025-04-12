package week4.day1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static week4.day1.ResponseAssertions.*;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;

import io.restassured.http.ContentType;
import week3.day2.InicidentReqPayload;

public class IncidentE2ETest extends ServiceNow {	
	
	String sys_id;
	
	static String update = """
			{             
              "description": "Update existing record using PUT method",
              "state": "2"
            }
			""";
	
	private String table_name = "incident";
	
	@Test
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
		        .contentType(ContentType.JSON)
		        .pathParam("tableName", table_name)
		        .log().all()
		        .when()
		        .body(incident)		        
		        .post("/{tableName}") // => baseURI + basePath + path
		        .then()
		        .log().all()
		        .assertThat()
		        .spec(validateResponseAsCreated())
		        .extract()
		        .jsonPath()
		        .getString("result.sys_id");
	}
	
	@Test(priority = 2)
	public void retrieve_a_record() {
		given()		
        .pathParam("tableName", table_name)
        .pathParam("sys_id", sys_id)
        .log().all()
        .when()
        .get("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .spec(validateResponseAsOk())
        .body("result.sys_id", Matchers.equalTo(sys_id))
        .time(Matchers.lessThan(2000L));
	}
	
	@Test(priority = 3)
	public void update_a_record() {
		given()			
        .pathParam("tableName", table_name)
        .pathParam("sys_id", sys_id)
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .body(update)
        .put("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .spec(validateResponseAsOk())
        .body("result.sys_id", Matchers.equalTo(sys_id))
        .body("result.state", Matchers.equalTo("2"));
	}
	
	@Test(priority = 4)
	public void delete_a_record() {
		given()		
        .pathParam("tableName", table_name)
        .pathParam("sys_id", sys_id)
        .when()
        .delete("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()
        .spec(validateResponseAsNoContent());		
	}
	
	@Test(priority = 5)
	public void validateIsDeletionSuccessfull() {
		given()		
        .auth().basic("admin", "d@9IvhOh5DR*")
        .pathParam("tableName", table_name)
        .pathParam("sys_id", sys_id)
        .log().all()
        .when()
        .get("/{tableName}/{sys_id}")
        .then()
        .log().all()
        .assertThat()     
        .spec(validateResponseAsNotFound())
        .time(Matchers.lessThan(2000L));
	}	

}