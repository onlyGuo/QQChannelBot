package bot.websocket;

import bot.constant.Opcode;
import bot.entity.Message;
import bot.entity.Payload;
import bot.service.QQChannelService;
import bot.util.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;

/**
 * WebSocket客户端
 *
 * @author 梁振辉
 * @since 2023-05-26 11:06:08
 */
public class CustomWebSocketClient extends WebSocketClient {

    private final Logger log = LoggerFactory.getLogger(CustomWebSocketClient.class);
    /**
     * WebSocket会话ID
     */
    private String sessionId;
    /**
     * 事件序号
     */
    private Integer seq;
    /**
     * QQ机器人认证Token
     */
    @Value("Bot ${bot.id}.${bot.token}")
    private String botToken;
    @Resource
    private QQChannelService qqChannelService;

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open " + sessionId);
    }

    @Override
    public void onMessage(String msg) {
        log.info(msg);
        Payload payload = JSONUtil.toBean(msg, Payload.class);
        switch (payload.getOp()) {
            case Opcode.HELLO -> hello();
            case Opcode.DISPATCH -> dispatch(payload);
        }
    }

    @Override
    public void onClose(int i, String msg, boolean b) {
        log.warn("close");
        new Thread(this::reconnect).start();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    private void heart() {
        if (sessionId != null) {
            ObjectNode data = JSONUtil.create()
                    .put("op", Opcode.HEARTBEAT)
                    .put("s", seq);
            send(data.toString());
        }
    }

    /**
     * 初始化连接
     */
    private void hello() {
        if (sessionId == null) {
            identify();
        } else {
            resume();
        }
    }

    /**
     * 鉴权连接
     */
    private void identify() {
        ObjectNode data = JSONUtil.create()
                .put("op", Opcode.IDENTIFY)
                .set("d", JSONUtil.create()
                        .put("token", botToken)
                        .put("intents", 1073742336)
                        .set("shard", JSONUtil.array(0, 1)));
        send(data.toString());
    }

    /**
     * 恢复连接
     */
    private void resume() {
        ObjectNode data = JSONUtil.create()
                .put("op", Opcode.RESUME)
                .set("d", JSONUtil.create()
                        .put("token", botToken)
                        .put("session_id", sessionId)
                        .put("seq", seq));
        send(data.toString());
    }

    /**
     * 处理服务端消息
     *
     * @param payload 服务端消息对象
     */
    private void dispatch(Payload payload) {
        seq = payload.getS();
        switch (payload.getT()) {
            case "READY" -> sessionId = payload.getD().get("session_id").asText();
            case "MESSAGE_CREATE" -> {
                Message msg = JSONUtil.toBean(payload.getD(), Message.class);
                if (msg.getMentions() != null && msg.getMentions().get(0).getBot()) {
                    //回复@消息
                    String content = msg.getContent().split(" ")[1];
                    Message Message = qqChannelService.message(msg.getId(), msg.getChannel_id(), content);
                    log.info(JSONUtil.toJson(Message));
                }
            }
        }
    }
}
