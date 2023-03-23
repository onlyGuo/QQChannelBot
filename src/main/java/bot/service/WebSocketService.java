package bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * WebSocket服务层
 */
@Service
public class WebSocketService {

    /**
     * 接口域名
     */
    @Value("${bot.api}")
    String botApi;

    @Resource
    RestTemplate restTemplate;

    /**
     * 获取WSS接入点
     *
     * @return WSS接入点
     */
    public String gateway() {
        String url = botApi + "/gateway";
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        if (response == null) {
            return "";
        }
        return response.get("url").asText();
    }


}
