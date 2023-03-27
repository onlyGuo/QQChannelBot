package bot.config;

import bot.entity.Gateway;
import bot.service.QQChannelService;
import bot.websocket.CustomWebSocketClient;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * WebSocket配置类
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Configuration
public class WebSocketConfig {

    @Resource
    QQChannelService QQChannelService;

    @Bean
    public WebSocketClient webSocketClient() {
        Gateway gateway = QQChannelService.gateway();
        URI uri = URI.create(gateway.getUrl());
        CustomWebSocketClient client = new CustomWebSocketClient(uri);
        client.connect();
        return client;
    }
}
