package bot.entitiy;

/**
 * payload 指的是在 websocket 连接上传输的数据，网关的上下行消息采用的都是同一个结构
 */
public class Payload {
    /**
     * opcode
     */
    private Integer op;
    /**
     * 下行消息都会有一个序列号，标识消息的唯一性，客户端需要再发送心跳的时候，携带客户端收到的最新的s
     */
    private Integer s;
    /**
     * 事件类型
     */
    private String t;
    /**
     * 事件内容
     */
    private Object d;

    public Payload() {
    }

    public Payload(Integer op, Integer s, String t, Object d) {
        this.op = op;
        this.s = s;
        this.t = t;
        this.d = d;
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

    public Object getD() {
        return d;
    }

    public void setD(Object d) {
        this.d = d;
    }
}
