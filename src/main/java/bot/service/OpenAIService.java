package bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenAI服务层
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Service
public class OpenAIService {

    @Resource
    RestTemplate restTemplate;

    public String chat(String content) {
        String url = "https://api.openai.com/v1/chat/completions";
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);
        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(message);
        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("messages", messages);
        JsonNode response = restTemplate.postForObject(url, data, JsonNode.class);
        if (response != null) {
            return response.get("choices").get(0).get("message").get("content").asText();
        } else {
            return "OpenAI调用异常";
        }
    }
}
