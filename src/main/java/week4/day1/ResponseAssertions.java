package week4.day1;

import org.hamcrest.Matchers;

import static io.restassured.RestAssured.expect;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseAssertions {
	
	public static ResponseSpecification validateResponseAsOk() {
		ResponseSpecification expect = expect();
		expect.statusCode(200);
		expect.statusLine(Matchers.containsString("OK"));
		expect.contentType(ContentType.JSON);
		return expect;
	}
	
	public static ResponseSpecification validateResponseAsCreated() {
		ResponseSpecification expect = expect();
		expect.statusCode(201);
		expect.statusLine(Matchers.containsString("Created"));
		expect.contentType(ContentType.JSON);
		return expect;
	}
	
	public static ResponseSpecification validateResponseAsNotFound() {
		ResponseSpecification expect = expect();
		expect.statusCode(404);
		expect.statusLine(Matchers.containsString("Not Found"));
		expect.contentType(ContentType.JSON);
		return expect;
	}
	
	public static ResponseSpecification validateResponseAsNoContent() {
		ResponseSpecification expect = expect();
		expect.statusCode(204);
		expect.statusLine(Matchers.containsString("No Content"));
		return expect;
	}

}