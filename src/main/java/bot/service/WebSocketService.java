package bot.service;

import bot.entity.Message;
import bot.entity.Payload;
import jakarta.annotation.Resource;
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

    @Resource
    private QQChannelService qqChannelService;

    @Resource
    private OpenAIService openAIService;


    public void reply(Payload payload) {
        String channelId = payload.getD().get("channel_id").asText();
        String msgId = payload.getD().get("id").asText();
        String content = payload.getD().get("content").asText();
        String gptContent = openAIService.chat(content);
        Map<String, Object> data = new HashMap<>();
        data.put("content", gptContent);
        data.put("msg_id", msgId);
        Message response = qqChannelService.send(channelId, data);
        System.out.println(response);
    }

}
