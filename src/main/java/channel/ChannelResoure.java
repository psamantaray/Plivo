package channel;

import java.util.HashMap;
import java.util.Map;

import RestAssuredUtils.EndPoints;
import RestAssuredUtils.RestAssuredUtils;
import io.restassured.response.Response;

public class ChannelResoure {

    public Response createChannel(String token, String channelName) {
        String baseUri = EndPoints.HOST + EndPoints.CREATE_CHANNEL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("name", channelName);
        Response response = RestAssuredUtils.postCall(baseUri, headers, params, null);

        System.out.println("Response Code:: " + response.getStatusCode());
        System.out.println("Response Body:: " + response.getBody().asString());
        return response;
    }

    public Response joinChannel(String token, String channelName) {
        String baseUri = EndPoints.HOST + EndPoints.JOIN_CHANNEL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("name", channelName);
        Response response = RestAssuredUtils.postCall(baseUri, headers, params, null);

        System.out.println("Response Code:: " + response.getStatusCode());
        System.out.println("Response Body:: " + response.getBody().asString());
        return response;
    }

    public Response renameChannel(String token, String channelId, String channelName) {
        String baseUri = EndPoints.HOST + EndPoints.RENAME_CHANNEL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("channel", channelId);
        params.put("name", channelName);
        Response response = RestAssuredUtils.postCall(baseUri, headers, params, null);

        System.out.println("Response Code:: " + response.getStatusCode());
        System.out.println("Response Body:: " + response.getBody().asString());
        return response;
    }

	public Response archiveChannel(String token, String channelId) {
		String baseUri = EndPoints.HOST + EndPoints.ARCHIVE_CHANNEL;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("channel", channelId);
		Response response = RestAssuredUtils.postCall(baseUri, headers, params, null);
		System.out.println("Response Code:: " + response.getStatusCode());
		System.out.println("Response Body:: " + response.getBody().asString());
		return response;
	}

	public Response getChannelsList(String token) {
		String baseUri = EndPoints.HOST + EndPoints.GET_CHANNELS;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		Response response = RestAssuredUtils.getCall(baseUri, headers, params);
		return response;
	}
}
