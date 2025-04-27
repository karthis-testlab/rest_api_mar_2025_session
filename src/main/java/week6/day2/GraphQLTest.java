package week6.day2;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GraphQLTest {
	
	String queryContent = """
			query {
  viewer {
    login
    name
    repositories {
      totalCount
      totalDiskUsage
    }
    followers {
      totalCount
    }
  }
}
			""";
	
	@Test
	public void githubtest() {
		RestAssured.given()
		           .baseUri("https://api.github.com")
		           .basePath("/graphql")
		           .header("Authorization", 
		        		   "Bearer <github_token>")
		           .log().all()
		           .when()
		           .body(convertQueryToJsonString())
		           .post()
		           .then()
		           .log().all()
		           .assertThat()
		           .statusCode(200);
		           
	}
	
	private String convertQueryToJsonString() {
		JSONObject json = new JSONObject();
		json.put("query", queryContent);
		return json.toString();
	}
}