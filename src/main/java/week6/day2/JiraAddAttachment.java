package week6.day2;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraAddAttachment {
	
	private String payload = """
			{
    "fields": {
        "summary": "Main order flow broken - 051020251401",
        "issuetype": {
            "id": "10012"
        },
        "project": {
            "id": "10003"
        }
    }
}
			""";
	private String issueId = "";
	
	@Test
	public void createNewIssue() {
		issueId = given()
		   .baseUri("https://karthikeselene.atlassian.net")
		   .basePath("/rest/api/3")
		   .auth()
		   .preemptive()
		   .basic("karthike.selene@gmail.com", "<jira_api_token>")
		   .contentType(ContentType.JSON)
		   .log().all()
		   .when()
		   .body(payload)
		   .post("/issue")
		   .then()
		   .log().all()
		   .assertThat()
		   .statusCode(201)
		   .extract()
		   .jsonPath()
		   .getString("id");
	}
	
	@Test
	public void uploadFileIntoTheIssue() {
		given()
		   .baseUri("https://karthikeselene.atlassian.net")
		   .basePath("/rest/api/3")
		   .auth()
		   .preemptive()
		   .basic("karthike.selene@gmail.com", "<jira_api_token>")
		   .contentType(ContentType.MULTIPART)
		   .header("X-Atlassian-Token", "no-check")
		   .log().all()
		   .when()
		   .multiPart(new File("./testng.xml"))
		   .post("/issue/"+issueId+"/attachments")
		   .then()
		   .log().all()
		   .assertThat()
		   .statusCode(200);
		
	}

}