package bot.service;

import bot.entity.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket服务层
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Service
public class WebSocketService {

    @Resource
    private QQChannelService qqChannelService;

    @Resource
    private GenshinService genshinService;

    @Resource
    private OpenAIService openAIService;


    public void genshinPool(JsonNode msg) {
        JsonNode pool = genshinService.pool();
        ArrayNode pools = pool.get("data").withArray("list");
        StringBuilder content = new StringBuilder();
        for (JsonNode p : pools) {
            content.append("\n\uD83D\uDCCC").append(p.get("title").asText()).append("\n")
                    .append(p.get("content_before_act").asText()).append("\n")
                    .append("开始时间：").append(p.get("start_time").asText()).append("\n")
                    .append("结束时间：").append(p.get("end_time").asText());
        }
        String channelId = msg.get("d").get("channel_id").asText();
        String msgId = msg.get("d").get("id").asText();
        Map<String, Object> data = new HashMap<>();
        data.put("content", content);
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        System.out.println(response);
    }

    public void systemInfo(JsonNode msg) {
        SystemInfo systemInfo = new SystemInfo();
        String channelId = msg.get("d").get("channel_id").asText();
        String msgId = msg.get("d").get("id").asText();
        Map<String, Object> data = new HashMap<>();
        data.put("content", "\n" + systemInfo.getOperatingSystem().toString()
                + "\n" + systemInfo.getHardware().getMemory());
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        System.out.println(response);
    }

    public void reply(JsonNode msg) {
        String channelId = msg.get("d").get("channel_id").asText();
        String msgId = msg.get("d").get("id").asText();
        String content = msg.get("d").get("content").asText();
        String gptContent = openAIService.chat(content);
        Map<String, Object> data = new HashMap<>();
        data.put("content", gptContent);
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        System.out.println(response);
    }

}
