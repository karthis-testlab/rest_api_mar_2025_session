package week3.day2;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;

import io.restassured.http.ContentType;

public class RequestChaining {
	
	static String create = """
			{
              "short_description": "RESTAPISessionMAR2025",
              "description": "Create new record using Post method"
            }
			""";
	
	static String update = """
			{
              "short_description": "RESTAPISessionMAR2025",
              "description": "Update existing record using PUT method",
              "state": "2"
            }
			""";

	public static void main(String[] args) {
		
		// POST -> Create a new Record
		String sys_id = given()
		.baseUri("https://dev265761.service-now.com")
        .basePath("/api/now/table")
        .auth().basic("admin", "d@9IvhOh5DR*")		           
        .contentType(ContentType.JSON)
        .pathParam("tableName", "incident")
        .log().all()
        .when()
        .body(create)
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
		
		// GET -> Retrieve created Record		
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
		
		// PUT - Update Existing record
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
        .body("result.sys_id", Matchers.equalTo(sys_id));
		
		// Delete - Delete the existing record
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
		
		// GET -> Validate Deletion		
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