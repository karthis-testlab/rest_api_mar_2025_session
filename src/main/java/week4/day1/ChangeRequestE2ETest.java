package week4.day1;

import static week4.day1.ResponseAssertions.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ChangeRequestE2ETest extends ServiceNow {
	
	private String table_name = "change_request";
	
	@Test
	public void retrieve_all_records() {
		given()		
        .pathParam("tableName", table_name)        
        .log().all()
        .when()
        .get("/{tableName}")
        .then()        
        .assertThat()
        .spec(validateResponseAsOk());
	}

}