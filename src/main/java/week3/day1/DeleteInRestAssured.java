package week3.day1;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;

public class DeleteInRestAssured {
	
	public static void main(String[] args) {
		given()
		    .baseUri("https://dev265761.service-now.com")
		    .basePath("/api/now/table")
		    .auth().basic("admin", "d@9IvhOh5DR*")
		    .pathParam("tableName", "incident")
		    .pathParam("sys_id", "09bfa994c3f4621082c2feac050131c3")
		    .log().all()
	   .when()
	        .delete("/{tableName}/{sys_id}")
	   .then()
	        .log().all()
	        .assertThat()
	        .statusCode(204)
	        .statusLine(Matchers.containsString("No Content"))
	        .time(Matchers.lessThan(4000L));
	        
	}

}