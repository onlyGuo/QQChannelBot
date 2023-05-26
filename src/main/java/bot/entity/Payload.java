package bot.entity;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * payload 指的是在 websocket 连接上传输的数据，网关的上下行消息采用的都是同一个结构
 *
 * @author 梁振辉
 * @since 2023-05-26 14:33:04
 */
@Data
public class Payload {
    private String id;
    private Integer op;
    private Integer s;
    private String t;
    private JSONObject d;
}
