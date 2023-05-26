package bot.websocket;

import bot.entity.Opcode;
import bot.entity.Payload;
import bot.util.JSONUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;
import java.util.TreeMap;

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
        if (payload != null) {
            switch (payload.getOp()) {
                case Opcode.HELLO -> {
                    if (sessionId == null) {
                        identify();
                    } else {
                        resume();
                    }
                }
                case Opcode.DISPATCH -> {
                    seq = payload.getS();
                    if (payload.getT().equals("READY")) {
                        sessionId = payload.getD().get("session_id").asText();
                    }
                }
            }
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
     * 鉴权连接
     */
    private void identify() {
        TreeMap<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.IDENTIFY);
            put("d", new TreeMap<>() {{
                put("token", botToken);
                put("intents", 512);
                put("shard", new int[]{0, 1});
            }});
        }};
        send(JSONUtil.toJson(data));
    }

    /**
     * 恢复连接
     */
    private void resume() {
        TreeMap<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.RESUME);
            put("d", new TreeMap<>() {{
                put("token", botToken);
                put("session_id", sessionId);
                put("seq", seq);
            }});
        }};
        send(JSONUtil.toJson(data));
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    private void heart() {
        if (sessionId != null) {
            TreeMap<String, Object> data = new TreeMap<>() {{
                put("op", Opcode.HEARTBEAT);
                put("s", seq);
            }};
            send(JSONUtil.toJson(data));
        }
    }
}
