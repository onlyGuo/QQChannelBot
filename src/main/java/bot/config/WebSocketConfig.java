package bot.config;

import bot.entity.Gateway;
import bot.service.QQChannelService;
import bot.websocket.CustomWebSocketClient;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author 梁振辉
 * @since 2023-05-26 11:11:22
 */
@Configuration
public class WebSocketConfig {

    @Resource
    QQChannelService qqChannelService;

    @Bean
    CustomWebSocketClient customWebSocketClient() {
        Gateway gateway = qqChannelService.gateway();
        URI uri = URI.create(gateway.getUrl());
        CustomWebSocketClient client = new CustomWebSocketClient(uri);
        client.connect();
        return client;
    }
}
