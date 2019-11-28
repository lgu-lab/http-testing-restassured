package org.demo.ws.rest;

import org.demo.data.record.AuthorRecord;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AuthorRest01IT extends AbstractRestClient {

	private static final String URI_ROOT = "http://localhost:3000" ;
			
	private static final String URI = URI_ROOT + "/author" ;

	private static final String URI_WITH_ID = URI_ROOT + "/author/123" ;

	private static final String BODY = 
		"{ " +
        "  \"id\": 123, " +
        "  \"firstName\": \"John\", " +
        "  \"lastName\" : \"Doe\" " +
        "}" ;
			
	@Test
	public void testScenario1() {

		Response response ; 
		
		//--- DELETE
		response = delete(URI_WITH_ID);
		// 204 - Deleted
		// 404 - Not found (not deleted)
		Assert.assertTrue(response.getStatusCode() == 204 || response.getStatusCode() == 404);

		//--- POST - CREATE
		response = post(URI, BODY);
		// 409 - Conflict if duplicate key 
		Assert.assertEquals(201, response.getStatusCode());  // 201 - Must be Created
		
		//--- GET - FIND BY ID
		response = get(URI_WITH_ID);
		Assert.assertEquals(200, response.getStatusCode() ); // 200 - Must be found
		//Assert.assertEquals("xxxx", response.getContentType());
		
		//String body2 = response.body().asString();
		JsonPath jsonPath = response.body().jsonPath();
		Assert.assertEquals(123, jsonPath.getInt("id") );
		
		AuthorRecord record = response.body().as(AuthorRecord.class);
		Assert.assertEquals(Integer.valueOf(123), record.getId());
		Assert.assertEquals("John", record.getFirstName());

		//--- DELETE
		response = delete(URI_WITH_ID);
		Assert.assertTrue(response.getStatusCode() == 204 ); // Must be found and deleted
	}

}
