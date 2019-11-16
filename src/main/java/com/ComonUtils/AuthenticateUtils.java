package com.ComonUtils;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthenticateUtils {
	
	public static String getToken(String userName, String password) {
		HashMap<String, String> request = new HashMap();
		HashMap<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		request.put("username", userName);
		request.put("password", password);
		String url = StaticData.host + StaticData.authenticate_api;
		Response response = RestAssured.given().baseUri(url).body(request).headers(headers).when().post().andReturn();
		String resp = response.getBody().asString();
		JSONObject json = null;
		String token = null;
		try {
			json = new JSONObject(resp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			token =  json.get("token").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return token;
	}
	
	public static void main(String[] args) {
		
			System.out.println(getToken("rupeek", "password"));
		
	}
}
