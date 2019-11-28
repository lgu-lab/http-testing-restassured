package org.demo.ws.rest;

import org.demo.data.record.AuthorRecord;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AuthorRest02IT extends AbstractRestClient {

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

		AuthorRecord record ;
		Response response ; 
		
		//--- DELETE
		response = delete(URI_WITH_ID);
		// 204 - Deleted
		// 404 - Not found (not deleted)
		Assert.assertTrue(response.getStatusCode() == 204 || response.getStatusCode() == 404);

		//--- POST - CREATE OK
		response = post(URI, BODY);
		Assert.assertEquals(201, response.getStatusCode());  // 201 - Created

		//--- POST - CREATE NOT OK (already exist) 
		record = new AuthorRecord();
		record.setId(123);
		record.setFirstName("Bart");
		record.setLastName("Simpson");
		response = post(URI, record);
		Assert.assertEquals(409, response.getStatusCode());  // 409 - Conflict (due to duplicate key) 
		
		//--- GET - FIND BY ID
		response = get(URI_WITH_ID);
		Assert.assertEquals(200, response.getStatusCode() ); // 200 - Must be found
		//log("getContentType() : " + response.getContentType() );
		Assert.assertEquals(RESPONSE_CONTENT_TYPE, response.getContentType());
		
		//String body2 = response.body().asString();
		
		// Check JSON text with JSON path
		JsonPath jsonPath = response.body().jsonPath();
		Assert.assertEquals(123, jsonPath.getInt("id") );
		Assert.assertEquals("John", jsonPath.getString("firstName") );
		Assert.assertEquals("Doe", jsonPath.getString("lastName") );
		
		// Convert JSON to object instance and check object attributes
		record = response.body().as(AuthorRecord.class);
		Assert.assertEquals(Integer.valueOf(123), record.getId());
		Assert.assertEquals("John", record.getFirstName());
		Assert.assertEquals("Doe", record.getLastName());

		//--- DELETE
		response = delete(URI_WITH_ID);
		Assert.assertTrue(response.getStatusCode() == 204 ); // Must be found and deleted
	}

}
