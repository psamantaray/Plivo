package RestAssuredUtils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtils {

	public static Response postCall(String uri, Map<String, String> headers, Map<String, String> params, Map<?,?> requestBody) {
		RequestSpecification request = getRequest(uri, headers, params, requestBody);
		Response response = request.when().post().andReturn();
		System.out.println("Response Code:: " + response.getStatusCode());
		System.out.println("Response Body:: " + response.getBody().asString());
		return response;
	}

	public static Response getCall(String uri, Map<String, String> headers, Map<String, String> params) {
		RequestSpecification request = getRequest(uri, headers, params, null);
		Response response = request.when().get().andReturn();
		System.out.println("Response Code:: " + response.getStatusCode());
		System.out.println("Response Body:: " + response.getBody().asString());
		return response;
	}

	private static RequestSpecification getRequest(String uri, Map<String, String> headers, Map<String, String> params,
			Map<?, ?> requestBody) {

		RequestSpecification request = RestAssured.given().baseUri(uri);
		if (headers != null)
			request.headers(headers);
		if (params != null)
			request.queryParams(params);
		if (requestBody != null)
			request.body(requestBody);

		return request;
	}

}
