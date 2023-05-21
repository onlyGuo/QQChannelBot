package bot.service;

import bot.entity.Message;
import bot.entity.Payload;
import bot.util.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    public static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    @Resource
    private QQChannelService qqChannelService;

    @Resource
    private OpenAIService openAIService;


    public void reply(Payload payload) {
        String channelId = payload.getD().get("channel_id").asText();
        String msgId = payload.getD().get("id").asText();
        String content = payload.getD().get("content").asText();
        //String gptContent = openAIService.chat(content);
        Map<String, Object> data = new HashMap<>();
        data.put("content", content);
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        log.info(JSONUtil.toJson(response));
    }

}
