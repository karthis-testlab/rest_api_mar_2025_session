package week3.day1;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import io.restassured.http.ContentType;

public class PathParameterUsingMapInRestAssured {

	public static void main(String[] args) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("table_name", "incident");
		map.put("sys_id", "1c832706732023002728660c4cf6a7b9");
		
		given()
		  .baseUri("https://dev265761.service-now.com")
          .basePath("/api/now/table")       
          .auth().basic("admin", "d@9IvhOh5DR*")          
          .pathParams(map)
          .log().all()
        .when()
          .get("/{table_name}/{sys_id}")         
        .then()
          .log().all()
          .assertThat()
          .statusCode(200)
          .contentType(ContentType.JSON);
		
		System.out.println("Test PASS!");

	}

}
