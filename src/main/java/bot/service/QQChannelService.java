package bot.service;

import bot.entity.Gateway;
import bot.entity.Message;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * QQ频道服务层
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Service
public class QQChannelService {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 用于获取 WSS 接入地址，通过该地址可建立 websocket 长连接
     *
     * @return 返回一个用于连接 websocket 的地址
     */
    public Gateway gateway() {
        String url = "https://sandbox.api.sgroup.qq.com/gateway";
        return restTemplate.getForObject(url, Gateway.class);
    }

    /**
     * 用于向 channel_id 指定的子频道发送消息。
     *
     * @param channelId 子频道ID
     * @param data      数据
     * @return Message对象
     */
    public Message send(String channelId, Map<String, Object> data) {
        String url = "https://sandbox.api.sgroup.qq.com/channels/" + channelId + "/messages";
        return restTemplate.postForObject(url, data, Message.class);
    }

}
