package TestSearchUser;

import java.io.IOException;
import java.util.HashMap;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ComonUtils.AuthenticateUtils;
import com.ComonUtils.StaticData;
import com.dto.SearchAllUser.SearchAllUserRequestDTO;
import com.dto.SearchAllUser.SerachUserRequest;
import com.dto.SearchAllUser.UserDetails;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class TestUser {
	String userName = "rupeek";
	String password = "password";

	@DataProvider(name = "DpForSearchAllUser")
	public Object[][] DpForSearchAllUser(){
		return new Object[][] {
			{new SearchAllUserRequestDTO(AuthenticateUtils.getToken(userName, password),"Passing valid token")},
			{new SearchAllUserRequestDTO("gfsdbdsjb","Passing invalid  token")},
			{new SearchAllUserRequestDTO(null,"Passing token as null")}
		};
	}
	
	@Test(dataProvider= "DpForSearchAllUser")
	public void testSearchAllUser(SearchAllUserRequestDTO request) {
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + request.getJwtToken());
		String url = StaticData.host + "/api/v1/users";
		Response response = RestAssured.given().baseUri(url).headers(headers).when().get().andReturn();
		String responseBody = RestAssured.given().baseUri(url).headers(headers).when().get().andReturn().body().asString();
		System.out.println(responseBody);
		
		if(request.getTestCase().equals("Passing valid token")) {
			Assert.assertEquals(response.getStatusCode(), 200, "Response code is not correct.");
			Assert.assertNotNull(responseBody, "Response Body is null.");
		}
		else if (request.getTestCase().equals("Passing invalid  token") || request.getTestCase().equals("Passing token as null")) {
			Assert.assertEquals(response.getStatusCode(), 401, "Response code is not correct.");
			Assert.assertEquals(responseBody, "", "Response body present for invalid token");

		}
	}
	
	@DataProvider(name = "DpForSearchAUser")
	public Object[][] DpForSearchAUser(){
		return new Object[][] {
			{new SerachUserRequest(AuthenticateUtils.getToken(userName, password),"8037602400", "Passing a valid user Phone")},
			{new SerachUserRequest(AuthenticateUtils.getToken(userName, password),"80376024001", "Passing a invalid user Phone")},
			{new SerachUserRequest("jjlbfdsbf","8037602400", "Passing a invalid token")},

		};
	}
	
	@Test(dataProvider= "DpForSearchAUser")
	public void testSearchAUser(SerachUserRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + request.getToken());
		String url = StaticData.host + "/api/v1/users/" + request.getPhone();
		Response response = RestAssured.given().baseUri(url).headers(headers).when().get().andReturn();
		String responseBody = RestAssured.given().baseUri(url).headers(headers).when().get().andReturn().body().asString();
		System.out.println("Response Body:: "+ responseBody);
		validateUserDetails(request, response);
	}
	
	private void validateUserDetails(SerachUserRequest request, Response response) throws JsonParseException, JsonMappingException, IOException {
		if(request == null || response == null) return;
		if(request.getTestCase().equals("Passing a valid user Phone")) {
			Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
			ObjectMapper mapper = new ObjectMapper();
			UserDetails userDetails = mapper.readValue(response.body().asString(), UserDetails.class);
			Assert.assertNotNull(userDetails.getFirst_name(), "First Name is null");
			Assert.assertNotNull(userDetails.getLast_name(), "Last Name is null");
			Assert.assertNotNull(userDetails.getCareer(), "Career Name is null");
			Assert.assertEquals(userDetails.getPhone(), request.getPhone(), "Phone number is not correct.");
		}
		else if(request.getTestCase().equals("Passing a invalid token")) {
			Assert.assertEquals(response.getStatusCode(), 401, "Response code is not correct.");
			Assert.assertEquals(response.getBody().asString(), "", "Response body present for invalid token");
		}
		else {
			Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
			Assert.assertEquals(response.getBody().asString(), "", "User details is not empty for invalid phone.");
		}
		
	}
	
	
}
