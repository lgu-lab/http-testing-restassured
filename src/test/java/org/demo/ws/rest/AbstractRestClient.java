package org.demo.ws.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public abstract class AbstractRestClient extends AbstractRestClientConst {

//	protected static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=utf-8" ;
	
	protected void log(String msg) {
		System.out.println("LOG : " + msg);
	}
	
	protected void log(Response response) {
		System.out.println("LOG : Response : " );
		System.out.println(" Status Code = " + response.getStatusCode());
		System.out.println(response.getBody().asString() );
		System.out.println("");
	}
	
	protected Response get(String uri) {
		log("GET " + uri);
		Response response = RestAssured.get(uri);
		log(response);
		return response;
	}

	protected Response delete(String uri) {
		log("DELETE " + uri);
		Response response = RestAssured.delete(uri);
		log(response);
		return response;
	}

	protected Response post(String uri, String body) {
		log("POST " + uri);
		Response response = RestAssured.given()
//				.contentType(ContentType.JSON)
				.contentType(REQUEST_CONTENT_TYPE)
				.body(body)
		.post(uri);
		log(response);
		return response;
	}

	protected Response post(String uri, Object object) {
		log("POST " + uri);
		Response response = RestAssured.given()
//				.contentType(ContentType.JSON)
				.contentType(REQUEST_CONTENT_TYPE)
				.body(object)
		.post(uri);
		log(response);
		return response;
	}

}
