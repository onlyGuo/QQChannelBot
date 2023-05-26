package bot.service;

import bot.entity.Gateway;
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

}
