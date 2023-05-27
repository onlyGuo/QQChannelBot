package bot.service;

import bot.entity.Gateway;
import bot.entity.Message;
import bot.entity.Payload;
import bot.util.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * QQ频道服务层
 *
 * @author 梁振辉
 * @since 2023-05-26 11:12:23
 */
@Service
public class QQChannelService {

    @Resource
    RestTemplate restTemplate;

    /**
     * 用于获取 WSS 接入地址，通过该地址可建立 websocket 长连接。
     *
     * @return 返回一个用于连接 websocket 的地址。
     */
    public Gateway gateway() {
        String url = "https://sandbox.api.sgroup.qq.com/gateway";
        return restTemplate.getForObject(url, Gateway.class);
    }

    /**
     * 用于向 channel_id 指定的子频道发送消息。
     *
     * @param payload WebSocket消息
     * @return Message对象
     */
    public Message message(Payload payload) {
        Message msg = JSONUtil.toBean(payload.getD().toString(), Message.class);
        String url = "https://sandbox.api.sgroup.qq.com/channels/" + msg.getChannel_id() + "/messages";
        ObjectNode data = JSONUtil.create()
                .put("msg_id", msg.getId())
                .put("content", msg.getContent());
        return restTemplate.postForObject(url, data, Message.class);

    }

}
