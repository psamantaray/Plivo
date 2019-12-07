import channel.ChannelListResponseDTO;
import channel.ChannelResoure;
import channel.Channels;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class Solution {
    ChannelResoure channelResource = new ChannelResoure();
    String token = "xoxp-855961922337-865762191895-866111451126-932572d58f0a5042b4ebc40a3375a750";

    @Test
    public void testChannelCreation(){
        Response response = channelResource.createChannel("token", "TestChannel1001");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
        JSONObject json = new JSONObject(response.getBody().asString());
        Assert.assertTrue(Boolean.parseBoolean(json.get("ok").toString()));
    }

    @Test
    public void testJoinChannel(){
        Response response = channelResource.joinChannel("token", "TestChannel1001");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
        JSONObject json = new JSONObject(response.getBody().asString());
        Assert.assertTrue(Boolean.parseBoolean(json.get("ok").toString()));
    }

    @Test
    public void renameChannel(){
        // Creating a channel
        Response response = channelResource.createChannel("token", "TestChannel1001");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
        JSONObject json = new JSONObject(response.getBody().asString());
        Assert.assertTrue(Boolean.parseBoolean(json.get("ok").toString()));

        String channel_id = json.getJSONObject("channel").get("id").toString();
        String newName = "NEWCHANNEL1";
        Response chanelRenameResp  = channelResource.renameChannel(token, channel_id, newName);

        // verifying whether the channel is renamed or not
        JSONObject channelRenameJsonResp = new JSONObject(chanelRenameResp.getBody().asString());
        String updated_name = channelRenameJsonResp.getJSONObject("channel").get("name").toString();

        Assert.assertTrue(updated_name.equals(newName), "name is not updated.");
    }

    @Test
    public void archiveChannel() throws IOException {
        // Creating a channel
        Response response = channelResource.createChannel("token", "TestChannel1001");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not correct.");
        JSONObject json = new JSONObject(response.getBody().asString());
        Assert.assertTrue(Boolean.parseBoolean(json.get("ok").toString()));

        String channel_id = json.getJSONObject("channel").get("id").toString();
        Response archiveChannelResp = channelResource.archiveChannel(token , channel_id);
        JSONObject channelRenameJsonResp = new JSONObject(archiveChannelResp.getBody().asString());
        Assert.assertTrue(Boolean.parseBoolean(channelRenameJsonResp.get("ok").toString()), "Channel is not archived.");

        // verifying whether the channel is deleted or not
        Response channelListResp = channelResource.getChannelsList(token);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ChannelListResponseDTO channelList = mapper.readValue(channelListResp.getBody().asString(), ChannelListResponseDTO.class);
        boolean isPresent = false;
        for(Channels c : channelList.getChannels() ){
            if(c.getId().equalsIgnoreCase(channel_id)){
                isPresent = true;
                break;
            }
        }
        System.out.println("Channel deleted status ::: " + !isPresent);
    }
}
