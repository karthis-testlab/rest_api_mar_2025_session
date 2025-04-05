package week3.day1;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;

public class QueryParameterInRestAssured {

	public static void main(String[] args) {	
		
		given()
		  .baseUri("https://dev265761.service-now.com")
          .basePath("/api/now/table")
          .auth().basic("admin", "d@9IvhOh5DR*")          
          .pathParam("table_name", "incident")
          .pathParam("sys_id", "1c832706732023002728660c4cf6a7b9")
          .queryParam("sysparm_fields", "sys_id,number,category,short_description,description")
          .log().all()
        .when()
          .get("/{table_name}/{sys_id}")         
        .then()
          .log().all()
          .assertThat()
          .statusCode(200)
          .body("result.sys_id", Matchers.equalTo("1c832706732023002728660c4cf6a7b9"));

	}

}
