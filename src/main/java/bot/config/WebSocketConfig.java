package bot.config;

import bot.service.WebSocketService;
import bot.websocket.CustomWebSocketClient;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * WebSocket配置类
 */
@Configuration
public class WebSocketConfig {

    @Resource
    WebSocketService webSocketService;

    @Bean
    public WebSocketClient webSocketClient() {
        //WSS接入点
        String gateway = webSocketService.gateway();
        URI uri = URI.create(gateway);
        CustomWebSocketClient client = new CustomWebSocketClient(uri);
        client.connect();
        return client;
    }
}
