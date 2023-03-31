package bot.service;

import bot.entity.Message;
import bot.util.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @Resource
    private QQChannelService qqChannelService;

    @Resource
    private OpenAIService openAIService;

    public void reply(JsonNode msg) {
        String channelId = msg.get("d").get("channel_id").asText();
        String msgId = msg.get("d").get("id").asText();
        String content = msg.get("d").get("content").asText();
        String gptContent = openAIService.chat(content);
        Map<String, Object> data = new HashMap<>();
        data.put("content", gptContent);
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        logger.info(JSONUtil.bean2json(response));
    }

}
