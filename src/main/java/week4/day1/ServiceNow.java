package week4.day1;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.basic;
import static week4.day1.PropertiesHandler.*;

import org.testng.annotations.BeforeTest;

public class ServiceNow {
	
	@BeforeTest
	public void setUp() {
		baseURI = config("service.now.base.uri");
		basePath = config("service.now.base.path");
		authentication = basic(config("service.now.username"), secret("service.now.password"));
	}

}