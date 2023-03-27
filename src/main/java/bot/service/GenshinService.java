package bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 原神数据服务层
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Service
public class GenshinService {

    @Resource
    RestTemplate restTemplate;

    public JsonNode pool() {
        String url = "https://api-takumi.mihoyo.com/common/blackboard/ys_obc/v1/gacha_pool?app_sn=ys_obc";
        return restTemplate.getForObject(url, JsonNode.class);
    }
}
