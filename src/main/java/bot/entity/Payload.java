package bot.entity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author 梁振辉
 * @since 2023-04-03 20:06:54
 */
public class Payload {

    private String id;
    private Integer op;
    private Integer s;
    private String t;
    private JsonNode d;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
