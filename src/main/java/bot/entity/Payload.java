package bot.entity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * payload 指的是在 websocket 连接上传输的数据，网关的上下行消息采用的都是同一个结构
 *
 * @author 梁振辉
 * @since 2023-05-26 14:33:04
 */
public class Payload {
    private Integer op;
    private Integer s;
    private String t;
    private JsonNode d;

    public Integer getOp() {
        return op;
    }

    public void setOp(Integer op) {
        this.op = op;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public JsonNode getD() {
        return d;
    }

    public void setD(JsonNode d) {
        this.d = d;
    }
}
